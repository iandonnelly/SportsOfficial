#include <pebble.h>
  
#define COUNTER_FONT_49 RESOURCE_ID_MACHINE_GUN_49
#define STOPWATCH_FONT_24 RESOURCE_ID_SPORTS_WORLD_24
#define HOME_AWAY_FONT_18 FONT_KEY_GOTHIC_18_BOLD
  
//---Counter Constants---
#define COUNTER_START 0
#define COUNTER_MAX 9999
#define COUNTER_MIN -9999
#define MAX_DIGITS 4
#define MAX_NUMBER_OF_SCORES 200
  
#define DEFAULT_SINGLE_CLICK 1
#define DEFUALT_DOUBLE_CLICK -1
#define DEFAULT_LONG_CLICK_KEY 5
  
//Define key numbers for data syncing
#define SYNC_KEY 0
#define HOME_SCORE_KEY 1
#define AWAY_SCORE_KEY 2
#define NUM_SCORES_KEY 3

#define SINGLE_CLICK_KEY 10
#define DOUBLE_CLICK_KEY 11
#define LONG_CLICK_KEY 12
  

#define DETAILED_POINTS_START 100
  
//Struct
typedef struct {
  char team;
  int points_scored;
  int teamACounter;
  int teamBCounter;
  int time;
} Scores;

//---Interface Variables---
Window* window;
static Layer* layer;
static GFont counterFont;
static GFont stopwatchFont;
static TextLayer* teamAScore_layer;
static TextLayer* teamBScore_layer;
static TextLayer* big_time_layer;
static TextLayer* seconds_time_layer;
static TextLayer* home_away_layer;


//---Counter Variables---
char teamA_counter_text[MAX_DIGITS + 2 /* sign & \0 */];
char teamB_counter_text[MAX_DIGITS + 2 /* sign & \0 */];
int teamACounter;
int teamBCounter;
int singleClickIncrement;
int longClickIncrement;
int doubleClickIncrement;

//---Holds score whenever scored---
Scores score;
Scores scoreArray[200];
int score_counter;

//---The Time
static double elapsed_time = 0;
static bool started = false;
static AppTimer* update_timer = NULL;
static double start_time = 0;
static double pause_time = 0;
time_t time_seconds();
void stop_stopwatch();
void start_stopwatch();
void handle_timer(void* data);
void update_stopwatch();

double float_time_ms() {
	time_t seconds;
	uint16_t milliseconds;
	time_ms(&seconds, &milliseconds);
	return (double)seconds + ((double)milliseconds / 1000.0);
}

void stop_stopwatch() {
    started = false;
	pause_time = float_time_ms();
    if(update_timer != NULL) {
        app_timer_cancel(update_timer);
        update_timer = NULL;
    }
}

void start_stopwatch() {
    started = true;
	if(start_time == 0) {
		start_time = float_time_ms();
	} else if(pause_time != 0) {
		double interval = float_time_ms() - pause_time;
		start_time += interval;
	}
    update_timer = app_timer_register(100, handle_timer, NULL);
}

void update_stopwatch() {
    static char big_time[] = "00:00";
    static char deciseconds_time[] = ".0";
    static char seconds_time[] = ":00";

    // Now convert to hours/minutes/seconds.
    int tenths = (int)(elapsed_time * 10) % 10;
    int seconds = (int)elapsed_time % 60;
    int minutes = (int)elapsed_time / 60 % 60;
    int hours = (int)elapsed_time / 3600;

    // We can't fit three digit hours, so stop timing here.
    if(hours > 99) {
        stop_stopwatch();
        return;
    }
	
	if(hours < 1) {
		snprintf(big_time, 6, "%02d:%02d", minutes, seconds);
		snprintf(deciseconds_time, 3, ".%d", tenths);
	} else {
		snprintf(big_time, 6, "%02d:%02d", hours, minutes);
		snprintf(seconds_time, 4, ":%02d", seconds);
	}

    // Now draw the strings.
    text_layer_set_text(big_time_layer, big_time);
    text_layer_set_text(seconds_time_layer, hours < 1 ? deciseconds_time : seconds_time);
}

//Sync function
static void sync(){
  //Create Tuplets for score
  Tuplet home_score = TupletInteger(HOME_SCORE_KEY, teamACounter);
  Tuplet away_score = TupletInteger(AWAY_SCORE_KEY, teamBCounter);
  Tuplet num_scored = TupletInteger(NUM_SCORES_KEY, score_counter);
  
  //Add to dict
  DictionaryIterator *iter;
  app_message_outbox_begin(&iter);
  dict_write_tuplet(iter, &home_score);
  dict_write_tuplet(iter, &away_score);
  
  //Error
  if (iter == NULL)
    return;
  
  //Sync score info
  //Tuplet score_info[score_counter];
  for(int i = 0; i < score_counter; i++){
    //Create tuplet for each score struct
    char * score_string = malloc(20);
    strcpy(score_string, "");
    snprintf(score_string, 20, "%c,%d,%d", scoreArray[i].team, scoreArray[i].points_scored, scoreArray[i].time);
    Tuplet score_info = TupletCString(i+100, score_string);
    
    //Add to dict
    dict_write_tuplet(iter, &score_info);
    
    free(score_string);
  }
  
  //End dict
  dict_write_end(iter);
  
  //Send it
  app_message_outbox_send();  
}

//AppMessage Callbacks
static void inbox_received_callback(DictionaryIterator *iterator, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Message received!");
  // Get the first pair
  Tuple *t = dict_read_first(iterator);

  // Process all pairs present
  while (t != NULL) {
    // Long lived buffer
    //static char s_buffer[64];

    // Process this pair's key
    switch (t->key) {
      case SINGLE_CLICK_KEY:
        // Set SINGLE_CLICK
        singleClickIncrement = t->value->int32;
        break;
      case DOUBLE_CLICK_KEY:
        //Set DOUBLE_CLICK
        doubleClickIncrement = t->value->int32;
        break;
      case LONG_CLICK_KEY:
        //Set LONG_CLICK
        longClickIncrement = t->value->int32;
        break;
      case SYNC_KEY:
        //Sync the motherfucker
        sync();
        break;
    }

    // Get next pair, if any
    t = dict_read_next(iterator);
  }
}

static void inbox_dropped_callback(AppMessageResult reason, void *context) {
  APP_LOG(APP_LOG_LEVEL_ERROR, "Message dropped!");
}

static void outbox_failed_callback(DictionaryIterator *iterator, AppMessageResult reason, void *context) {
  APP_LOG(APP_LOG_LEVEL_ERROR, "Outbox send failed!");
}

static void outbox_sent_callback(DictionaryIterator *iterator, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Outbox send success!");
}


//Click Handlers
void select_click_long_handler(ClickRecognizerRef recognizer, Window *window) {           //pressed SELECT LONG
    bool is_running = started;
    stop_stopwatch();
    start_time = 0;
	elapsed_time = 0;
    if(is_running) start_stopwatch();
    update_stopwatch();
}

static void select_click_handler(ClickRecognizerRef recognizer, void *context) {         //pressed SELECT 
  if(started) {
        stop_stopwatch();
    } else {
        start_stopwatch();
    }
}

void handle_timer(void* data) {
	if(started) {
		double now = float_time_ms();
		elapsed_time = now - start_time;
		update_timer = app_timer_register(100, handle_timer, NULL);
	}
	update_stopwatch();
}

//------------------------------------------------------------------------>Here holds when anyone scores!!<---
static void score_change(bool home){
  if(score_counter >= MAX_NUMBER_OF_SCORES - 1)
    score_counter = 0;
  
  if(home){
    score.team = 'H';
    if(score_counter == 0)
      score.points_scored = teamACounter;
    else
      score.points_scored = teamACounter - scoreArray[score_counter-1].teamACounter;
  }
  else{
    score.team = 'A';
    if(score_counter == 0)
      score.points_scored = teamBCounter;
    else
      score.points_scored = teamBCounter - scoreArray[score_counter-1].teamBCounter;
  }
  
  score.teamACounter = teamACounter;
  score.teamBCounter = teamBCounter;
  score.time = (int)elapsed_time;
  
  //Holds team that scored, team A score, team B score
  scoreArray[score_counter] = score;  //<-------------------------------------holds the scores<----
  
  score_counter++;
}

static int increment_value(int team_score, const int increment){
  if(team_score + increment <= COUNTER_MAX && team_score + increment >= COUNTER_MIN)
     team_score = team_score + increment;
  
  return team_score;
}

static void up_multi_click_handler(ClickRecognizerRef recognizer, void *context) {       //pressed UP MULTI
  teamACounter = increment_value(teamACounter, doubleClickIncrement);
  score_change(true);
  layer_mark_dirty(layer);
}

static void up_click_long_handler(ClickRecognizerRef recognizer, void *context) {        //pressed UP LONG
  teamACounter = increment_value(teamACounter, longClickIncrement);
  score_change(true);
  layer_mark_dirty(layer);
}

static void up_click_handler(ClickRecognizerRef recognizer, void *context) {             //pressed UP 
  teamACounter = increment_value(teamACounter, singleClickIncrement);
  score_change(true);
  layer_mark_dirty(layer);
}

static void down_multi_click_handler(ClickRecognizerRef recognizer, void *context) {     //pressed DOWN MULTI
  teamBCounter = increment_value(teamBCounter, doubleClickIncrement);
  score_change(false);
  layer_mark_dirty(layer);
}

static void down_click_long_handler(ClickRecognizerRef recognizer, void *context) {     //pressed DOWN LONG
  teamBCounter = increment_value(teamBCounter, longClickIncrement);
  score_change(false);
  layer_mark_dirty(layer);
}

static void down_click_handler(ClickRecognizerRef recognizer, void *context) {          //pressed DOWN
  teamBCounter = increment_value(teamBCounter, singleClickIncrement);
  score_change(false);
  layer_mark_dirty(layer);
}

static void click_config_provider(void *context) {
  window_single_click_subscribe(BUTTON_ID_SELECT, select_click_handler);
  window_long_click_subscribe(BUTTON_ID_SELECT, 700, (ClickHandler) select_click_long_handler, NULL);
   
  window_single_click_subscribe(BUTTON_ID_UP, up_click_handler);
  window_long_click_subscribe(BUTTON_ID_UP, 700, (ClickHandler)up_click_long_handler, NULL);
  window_multi_click_subscribe(BUTTON_ID_UP, 2, 10, 0, true, up_multi_click_handler);
  
  window_single_click_subscribe(BUTTON_ID_DOWN, down_click_handler);
  window_long_click_subscribe(BUTTON_ID_DOWN, 700, (ClickHandler)down_click_long_handler, NULL);
  window_multi_click_subscribe(BUTTON_ID_DOWN, 2, 10, 0, true, down_multi_click_handler);
}

static void update_layer(Layer *layer, GContext *ctx){
  GRect bounds = layer_get_frame(layer);
  
  graphics_context_set_text_color(ctx, GColorBlack);
  
  snprintf(teamA_counter_text, (MAX_DIGITS + 2)*sizeof(char), "%d", teamACounter);
  graphics_draw_text(ctx, 
                     teamA_counter_text, 
                     counterFont, 
                     GRect(0, 0, bounds.size.w - 10, 60),
                     GTextOverflowModeWordWrap, 
                     GTextAlignmentCenter, 
                     NULL);
  
  snprintf(teamB_counter_text, (MAX_DIGITS + 2)*sizeof(char), "%d", teamBCounter);
  graphics_draw_text(ctx, 
                     teamB_counter_text,
                     counterFont, 
                     GRect(0, 95, bounds.size.w - 10, 60), 
                     GTextOverflowModeWordWrap,
                     GTextAlignmentCenter, 
                     NULL);
}

static void window_load(Window *window) {
  // Get the root layer
  Layer *window_layer = window_get_root_layer(window);
  // Get the bounds of the window for sizing the text layer
  GRect bounds = layer_get_bounds(window_layer);
  
  layer = layer_create(bounds);
  layer_set_update_proc(layer, update_layer);
  layer_add_child(window_layer, layer);
}

static void window_unload(Window *window) {
  // Destroy TextLayer
  text_layer_destroy(teamAScore_layer);
  text_layer_destroy(teamBScore_layer);
  text_layer_destroy(big_time_layer);
  text_layer_destroy(seconds_time_layer);
}

void handle_init(void) {
  // Register callbacks
  app_message_register_inbox_received(inbox_received_callback);
  app_message_register_inbox_dropped(inbox_dropped_callback);
  app_message_register_outbox_failed(outbox_failed_callback);
  app_message_register_outbox_sent(outbox_sent_callback);
  
  // Open AppMessage
  app_message_open(app_message_inbox_size_maximum(), app_message_outbox_size_maximum());
  
  //Build Window
  window = window_create();
  window_set_click_config_provider(window, click_config_provider);
  window_set_window_handlers(window, (WindowHandlers) {
	  .load = window_load,
    .unload = window_unload,
  });
  
  //Set the counters start
  teamACounter = COUNTER_START;
  teamBCounter = COUNTER_START;
  score_counter = 0;
  
//-----Temp Values----- 
  singleClickIncrement = DEFAULT_SINGLE_CLICK;
  doubleClickIncrement = DEFUALT_DOUBLE_CLICK;
  longClickIncrement   = DEFAULT_LONG_CLICK_KEY;
  
//-----Stored Values-----
  if(persist_exists(SINGLE_CLICK_KEY))
     singleClickIncrement = persist_read_int(SINGLE_CLICK_KEY);
  if(persist_exists(DOUBLE_CLICK_KEY))
     singleClickIncrement = persist_read_int(DOUBLE_CLICK_KEY);
  if(persist_exists(LONG_CLICK_KEY))
     singleClickIncrement = persist_read_int(LONG_CLICK_KEY);
  
  Layer *root_layer = window_get_root_layer(window);
  stopwatchFont = fonts_load_custom_font(resource_get_handle(STOPWATCH_FONT_24));
  counterFont = fonts_load_custom_font(resource_get_handle(COUNTER_FONT_49));
  
  //-----Display Stop Watch-----
  big_time_layer = text_layer_create(GRect(0, 65, 86, 35));
  text_layer_set_text_alignment(big_time_layer, GTextAlignmentRight);
  text_layer_set_background_color(big_time_layer, GColorClear);
  text_layer_set_text(big_time_layer, "00:00");
  text_layer_set_font(big_time_layer, stopwatchFont);
  layer_add_child(root_layer, (Layer*)big_time_layer);
  
  seconds_time_layer = text_layer_create(GRect(86, 65, 49, 35));
  text_layer_set_text(seconds_time_layer, ".0");
  text_layer_set_background_color(seconds_time_layer, GColorClear);
  text_layer_set_font(seconds_time_layer, stopwatchFont);
  layer_add_child(root_layer, (Layer*)seconds_time_layer);
  
  //-----Display Home and Away-----
  home_away_layer = text_layer_create(GRect(124, 0, 20, 152));
  text_layer_set_text(home_away_layer, "\n H\n\n\n\n\n A");
  text_layer_set_background_color(home_away_layer, GColorBlack);
  text_layer_set_text_color(home_away_layer, GColorWhite);
  text_layer_set_font(home_away_layer, fonts_get_system_font(HOME_AWAY_FONT_18));
  layer_add_child(root_layer, (Layer*)home_away_layer);
  
  const bool animated = true;
  window_stack_push(window, animated);
}

void handle_deinit(void) {
  window_destroy(window);
}

int main(void) {
  handle_init();
  app_event_loop();
  handle_deinit();
}
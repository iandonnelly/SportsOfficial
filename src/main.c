#include <pebble.h>
#include "data.h"
#include "button_click.h"
#include "interface.h"
#include "stopwatch.h"
#include "sync.h"
  
//Init externs
int homeCounter;
int awayCounter;
int score_counter;
int singleClickIncrement;
int doubleClickIncrement;
int longClickIncrement;
GFont stopwatchFont;
GFont counterFont;
TextLayer* big_time_layer;
TextLayer* seconds_time_layer;
TextLayer* home_away_layer;
TextLayer* homeScore_layer;
TextLayer* awayScore_layer;
Window* window;
Layer* layer;
char home_counter_text[MAX_DIGITS + 2];
char away_counter_text[MAX_DIGITS + 2];
Scores score;
Scores scoreArray[200];
  
static void init(void) {
// Register callbacks
  app_message_register_inbox_received(inbox_received_callback);
  app_message_register_inbox_dropped(inbox_dropped_callback);
  //app_message_register_outbox_failed(outbox_failed_callback);
  //app_message_register_outbox_sent(outbox_sent_callback);
  
  
  // Open AppMessage
  app_message_open(app_message_inbox_size_maximum(), app_message_outbox_size_maximum());
  init_stopwatch();
  
  //Build Window
  window = window_create();
  
  //Colors
  #ifdef PBL_COLOR
    window_set_background_color(window, GColorBlack);
  #else
    window_set_background_color(window, GColorWhite);
  #endif
    
  window_set_click_config_provider(window, click_config_provider);
  window_set_window_handlers(window, (WindowHandlers) {
	  .load = window_load,
    .unload = window_unload,
  });
  
  //Set the counters start
  homeCounter = COUNTER_START;
  awayCounter = COUNTER_START;
  score_counter = 0;
  
//-----Temp Values----- 
  singleClickIncrement = DEFAULT_SINGLE_CLICK;
  doubleClickIncrement = DEFUALT_DOUBLE_CLICK;
  longClickIncrement   = DEFAULT_LONG_CLICK_KEY;
  
//-----Stored Values-----
  if(persist_exists(SINGLE_CLICK_KEY))
    singleClickIncrement = persist_read_int(SINGLE_CLICK_KEY);
  if(persist_exists(DOUBLE_CLICK_KEY))
     doubleClickIncrement = persist_read_int(DOUBLE_CLICK_KEY);
  if(persist_exists(LONG_CLICK_KEY))
     longClickIncrement = persist_read_int(LONG_CLICK_KEY);
  
  Layer *root_layer = window_get_root_layer(window);
  stopwatchFont = fonts_load_custom_font(resource_get_handle(STOPWATCH_FONT_24));
  counterFont = fonts_load_custom_font(resource_get_handle(COUNTER_FONT_49));  
  
  //Colors
  #ifdef PBL_COLOR
    big_time_layer = text_layer_create(GRect(0, 81, 86, 35));
    text_layer_set_text_alignment(big_time_layer, GTextAlignmentRight);
    text_layer_set_background_color(big_time_layer, GColorBlack);
    text_layer_set_text_color(big_time_layer, GColorYellow);
    seconds_time_layer = text_layer_create(GRect(86, 81, 49, 35));
  #else
    big_time_layer = text_layer_create(GRect(0, 65, 86, 35));
    text_layer_set_text_alignment(big_time_layer, GTextAlignmentRight);
    text_layer_set_background_color(big_time_layer, GColorClear);
    text_layer_set_text_color(big_time_layer, GColorBlack);
    seconds_time_layer = text_layer_create(GRect(86, 65, 49, 35));
  #endif
    
  text_layer_set_text(big_time_layer, "00:00");
  text_layer_set_font(big_time_layer, stopwatchFont);
  layer_add_child(root_layer, (Layer*)big_time_layer);
  text_layer_set_text(seconds_time_layer, ".0");
  
  //Color
  #ifdef PBL_COLOR
    text_layer_set_background_color(seconds_time_layer, GColorBlack);
    text_layer_set_text_color(seconds_time_layer, GColorYellow);
  #else
    text_layer_set_background_color(seconds_time_layer, GColorClear);
    text_layer_set_text_color(seconds_time_layer, GColorBlack);
  #endif
  
  text_layer_set_font(seconds_time_layer, stopwatchFont);
  layer_add_child(root_layer, (Layer*)seconds_time_layer);
  
  //-----Display Home and Away-----
  #ifdef PBL_COLOR
    home_away_layer = text_layer_create(GRect(124, 16, 20, 152));
  #else
    home_away_layer = text_layer_create(GRect(124, 0, 20, 152));
  #endif
  
  text_layer_set_text(home_away_layer, "\n H\n\n\n\n\n A");
  
  //Colors
  #ifdef PBL_COLOR
    text_layer_set_text_color(home_away_layer, GColorBlack);
    text_layer_set_background_color(home_away_layer, GColorYellow);
  #else
    text_layer_set_text_color(home_away_layer, GColorWhite);
    text_layer_set_background_color(home_away_layer, GColorBlack);
  #endif
  
  text_layer_set_font(home_away_layer, fonts_get_system_font(HOME_AWAY_FONT_18));
  layer_add_child(root_layer, (Layer*)home_away_layer);
  
  const bool animated = true;
  window_stack_push(window, animated);
}

static void deinit(void) {
  window_destroy(window);
}

int main(void) {
  init();
  app_event_loop();
  deinit();
}
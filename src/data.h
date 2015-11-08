#ifndef DATA_H
#define DATA_H

#include <pebble.h>

//Resources
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
  int homeCounter;
  int awayCounter;
  int time;
} Scores;

//---Interface Variables---
extern Window* window;
extern Layer* layer;
extern GFont counterFont;
extern GFont stopwatchFont;
extern TextLayer* homeScore_layer;
extern TextLayer* awayScore_layer;
extern TextLayer* big_time_layer;
extern TextLayer* seconds_time_layer;
extern TextLayer* home_away_layer;
#ifdef PBL_SDK_3
extern StatusBarLayer *s_status_bar;
#endif


//---Counter Variables---
extern char home_counter_text[MAX_DIGITS + 2 /* sign & \0 */];
extern char away_counter_text[MAX_DIGITS + 2 /* sign & \0 */];
extern int homeCounter;
extern int awayCounter;
extern int singleClickIncrement;
extern int longClickIncrement;
extern int doubleClickIncrement;

//---Holds score whenever scored---
extern Scores score;
extern Scores scoreArray[200];
extern int score_counter;

//---The Time
extern double elapsed_time;
extern bool started;
extern AppTimer* update_timer;
extern double start_time;
extern double pause_time;

#endif
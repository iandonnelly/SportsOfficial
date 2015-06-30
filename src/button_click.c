#include <pebble.h>
#include "button_click.h"
#include "score.h"
#include "stopwatch.h"

void up_multi_click_handler(ClickRecognizerRef recognizer, void *context) {       //pressed UP MULTI
  homeCounter = increment_value(homeCounter, doubleClickIncrement);
  score_change(true);
  layer_mark_dirty(layer);
}

void up_click_long_handler(ClickRecognizerRef recognizer, void *context) {        //pressed UP LONG
  homeCounter = increment_value(homeCounter, longClickIncrement);
  score_change(true);
  layer_mark_dirty(layer);
}

void up_click_handler(ClickRecognizerRef recognizer, void *context) {             //pressed UP 
  homeCounter = increment_value(homeCounter, singleClickIncrement);
  score_change(true);
  layer_mark_dirty(layer);
}

void down_multi_click_handler(ClickRecognizerRef recognizer, void *context) {     //pressed DOWN MULTI
  awayCounter = increment_value(awayCounter, doubleClickIncrement);
  score_change(false);
  layer_mark_dirty(layer);
}

void down_click_long_handler(ClickRecognizerRef recognizer, void *context) {     //pressed DOWN LONG
  awayCounter = increment_value(awayCounter, longClickIncrement);
  score_change(false);
  layer_mark_dirty(layer);
}

void down_click_handler(ClickRecognizerRef recognizer, void *context) {          //pressed DOWN
  awayCounter = increment_value(awayCounter, singleClickIncrement);
  score_change(false);
  layer_mark_dirty(layer);
}

void select_click_long_handler(ClickRecognizerRef recognizer, Window *window) {           //pressed SELECT LONG
    bool is_running = started;
    stop_stopwatch();
    start_time = 0;
	elapsed_time = 0;
    if(is_running) start_stopwatch();
    update_stopwatch();
}

void select_click_handler(ClickRecognizerRef recognizer, void *context) {         //pressed SELECT 
  if(started) {
        stop_stopwatch();
    } else {
        start_stopwatch();
    }
}

void click_config_provider(void *context) {
  window_single_click_subscribe(BUTTON_ID_SELECT, select_click_handler);
  window_long_click_subscribe(BUTTON_ID_SELECT, 700, (ClickHandler) select_click_long_handler, NULL);
   
  window_single_click_subscribe(BUTTON_ID_UP, up_click_handler);
  window_long_click_subscribe(BUTTON_ID_UP, 700, (ClickHandler)up_click_long_handler, NULL);
  window_multi_click_subscribe(BUTTON_ID_UP, 2, 10, 0, true, up_multi_click_handler);
  
  window_single_click_subscribe(BUTTON_ID_DOWN, down_click_handler);
  window_long_click_subscribe(BUTTON_ID_DOWN, 700, (ClickHandler)down_click_long_handler, NULL);
  window_multi_click_subscribe(BUTTON_ID_DOWN, 2, 10, 0, true, down_multi_click_handler);
}
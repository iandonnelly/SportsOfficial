#include <pebble.h>
#include "interface.h"

void update_layer(Layer *layer, GContext *ctx){
  GRect bounds = layer_get_frame(layer);
  
  //Handle Colors!
  #ifdef PBL_COLOR //If a color display
    graphics_context_set_text_color(ctx, GColorYellow);
    //graphics_context_set_fill_color(ctx, GColorBlack);
  #else
    graphics_context_set_text_color(ctx, GColorBlack);
    //graphics_context_set_fill_color(ctx, GColorWhite);
  #endif
  
  snprintf(home_counter_text, (MAX_DIGITS + 2)*sizeof(char), "%d", homeCounter);
  graphics_draw_text(ctx, 
                     home_counter_text, 
                     counterFont, 
                     GRect(0, 0, bounds.size.w - 10, 60),
                     GTextOverflowModeWordWrap, 
                     GTextAlignmentCenter, 
                     NULL);
  
  snprintf(away_counter_text, (MAX_DIGITS + 2)*sizeof(char), "%d", awayCounter);
  graphics_draw_text(ctx, 
                     away_counter_text,
                     counterFont, 
                     GRect(0, 95, bounds.size.w - 10, 60), 
                     GTextOverflowModeWordWrap,
                     GTextAlignmentCenter, 
                     NULL);
}

void window_load(Window *window) {
  // Get the root layer
  Layer *window_layer = window_get_root_layer(window);
  // Get the bounds of the window for sizing the text layer
  GRect bounds = layer_get_bounds(window_layer);
  
  layer = layer_create(bounds);
  layer_set_update_proc(layer, update_layer);
  layer_add_child(window_layer, layer);
}

void window_unload(Window *window) {
  // Destroy TextLayer
  text_layer_destroy(homeScore_layer);
  text_layer_destroy(awayScore_layer);
  text_layer_destroy(big_time_layer);
  text_layer_destroy(seconds_time_layer);
}
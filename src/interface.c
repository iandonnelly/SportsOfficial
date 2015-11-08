#include <pebble.h>
#include "interface.h"

void update_layer(Layer *layer, GContext *ctx){
  GRect bounds = layer_get_frame(layer);
  GRect basalt_offset = GRect(0, 0, 0, 0);
  
  //Handle Colors!
  #ifdef PBL_COLOR //If a color display
    graphics_context_set_text_color(ctx, GColorYellow);
    basalt_offset = GRect(0, 16, 0, 0);
    //graphics_context_set_fill_color(ctx, GColorBlack);
  #else
    graphics_context_set_text_color(ctx, GColorBlack);
    //graphics_context_set_fill_color(ctx, GColorWhite);
  #endif
  
  snprintf(home_counter_text, (MAX_DIGITS + 2)*sizeof(char), "%d", homeCounter);
  graphics_draw_text(ctx, 
                     home_counter_text, 
                     counterFont, 
                     GRect(0, 0 + basalt_offset.origin.y, bounds.size.w - 10, 60),
                     GTextOverflowModeWordWrap, 
                     GTextAlignmentCenter, 
                     NULL);
  
  snprintf(away_counter_text, (MAX_DIGITS + 2)*sizeof(char), "%d", awayCounter);
  graphics_draw_text(ctx, 
                     away_counter_text,
                     counterFont, 
                     GRect(0, 95 + + basalt_offset.origin.y, bounds.size.w - 10, 60), 
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
  
  #ifdef PBL_SDK_3
    // Set up the status bar last to ensure it is on top of other Layers
    StatusBarLayer *s_status_bar = status_bar_layer_create();
    status_bar_layer_set_separator_mode(s_status_bar, StatusBarLayerSeparatorModeDotted);
    status_bar_layer_set_colors(s_status_bar, GColorClear, GColorYellow);
    layer_add_child(window_layer, status_bar_layer_get_layer(s_status_bar));
  #endif
}

void window_unload(Window *window) {
  // Destroy TextLayer
  text_layer_destroy(homeScore_layer);
  text_layer_destroy(awayScore_layer);
  text_layer_destroy(big_time_layer);
  text_layer_destroy(seconds_time_layer);
}
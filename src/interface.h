#ifndef INTERFACE_H
#define INTERFACE_H
#include "data.h"
  
#pragma once

void update_layer(Layer *layer, GContext *ctx);
void window_load(Window *window);
void window_unload(Window *window);

#endif
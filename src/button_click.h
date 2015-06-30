#ifndef BUTTON_CLICK_H
#define BUTTON_CLICK_H
#include "data.h"

void up_multi_click_handler(ClickRecognizerRef recognizer, void *context);
void up_click_long_handler(ClickRecognizerRef recognizer, void *context);
void up_click_handler(ClickRecognizerRef recognizer, void *context);
void down_multi_click_handler(ClickRecognizerRef recognizer, void *context) ;
void down_click_long_handler(ClickRecognizerRef recognizer, void *context);
void down_click_handler(ClickRecognizerRef recognizer, void *context);
void select_click_long_handler(ClickRecognizerRef recognizer, Window *window);
void select_click_handler(ClickRecognizerRef recognizer, void *context);
void click_config_provider(void *context);

#endif
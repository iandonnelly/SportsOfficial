#ifndef STOPWATCH_H
#define STOPWATCH_H
#include "data.h"
#pragma once

double float_time_ms();  
void start_stopwatch();
void update_stopwatch();
void stop_stopwatch();
void init_stopwatch();
void reset_stopwatch();
void handle_timer(void* data);

#endif
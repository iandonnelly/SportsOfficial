#ifndef SYNC_H
#define SYNC_H
#include "data.h"
  
//AppMessage Callbacks
void inbox_received_callback(DictionaryIterator *iterator, void *context);
void inbox_dropped_callback(AppMessageResult reason, void *context);
  
  
#endif
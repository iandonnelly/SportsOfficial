#include <pebble.h>
#include "sync.h"

//AppMessage Callbacks
void inbox_received_callback(DictionaryIterator *iterator, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Message received!");
  // Get the first pair
  Tuple *t = dict_read_first(iterator);
  
  if(t==NULL)
    APP_LOG(APP_LOG_LEVEL_ERROR, "Dictionary Empty!");
  // Process all pairs present
  while (t != NULL) {
    // Long lived buffer
    //static char s_buffer[64];  

    // Process this pair's key
    switch (t->key) {
      case SINGLE_CLICK_KEY:
        // Set SINGLE_CLICK      
        APP_LOG(APP_LOG_LEVEL_INFO, "singleClickMessage");
        APP_LOG(APP_LOG_LEVEL_INFO, "singleClickMessage = %ld",  t->value->int32);
        singleClickIncrement = t->value->int32;
        persist_write_int(SINGLE_CLICK_KEY, singleClickIncrement);
        break;
      case DOUBLE_CLICK_KEY:
        //Set DOUBLE_CLICK
        doubleClickIncrement = t->value->int32;
        persist_write_int(DOUBLE_CLICK_KEY, doubleClickIncrement);
        break;
      case LONG_CLICK_KEY:
        //Set LONG_CLICK
        longClickIncrement = t->value->int32;
        persist_write_int(LONG_CLICK_KEY, longClickIncrement);
        break;
      /*
      case SYNC_KEY:
        //Sync the motherfucker
        sync();
        break;
      */
      default:
        APP_LOG(APP_LOG_LEVEL_ERROR, "Key not recognized.");
    }

    // Get next pair, if any
    t = dict_read_next(iterator);
  }
}

void inbox_dropped_callback(AppMessageResult reason, void *context) {
  APP_LOG(APP_LOG_LEVEL_ERROR, "Message dropped!");
}
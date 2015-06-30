#include <pebble.h>
#include "score.h" 
  
int score_change(char team){
  if(score_counter >= MAX_NUMBER_OF_SCORES - 1)
    score_counter = 0;
  
  //Home team scored
  if(team == 'H'){
    score.team = 'H';
    if(score_counter == 0)
      score.points_scored = homeCounter;
    else
      score.points_scored = homeCounter - scoreArray[score_counter-1].homeCounter;
  }
  
  //Away team scored
  else if(team == 'A'){
    score.team = 'A';
    if(score_counter == 0)
      score.points_scored = awayCounter;
    else
      score.points_scored = awayCounter - scoreArray[score_counter-1].awayCounter;
  }
  
  //Bad call
  else
    return -1;
  
  score.homeCounter = homeCounter;
  score.awayCounter = awayCounter;
  score.time = (int)elapsed_time;
  
  //Holds team that scored, home score, away score
  scoreArray[score_counter] = score;
  
  score_counter++;
  return 0;
}


int increment_value(int team_score, const int increment){
  if(team_score + increment <= COUNTER_MAX && team_score + increment >= COUNTER_MIN)
     team_score = team_score + increment;
  
  return team_score;
}
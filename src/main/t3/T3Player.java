package main.t3;

import java.util.*;
import java.util.Map.Entry;


/**
 * Artificial Intelligence responsible for playing the game of T3!
 * Implements the alpha-beta-pruning mini-max search algorithm
 */
public class T3Player {
    
    /**
     * Workhorse of an AI T3Player's choice mechanics that, given a game state,
     * makes the optimal choice from that state as defined by the mechanics of
     * the game of Tic-Tac-Total.
     * Note: In the event that multiple moves have equivalently maximal minimax
     * scores, ties are broken by move col, then row, then move number in ascending
     * order (see spec and unit tests for more info). The agent will also always
     * take an immediately winning move over a delayed one (e.g., 2 moves in the future).
     * @param state The state from which the T3Player is making a move decision.
     * @return The T3Player's optimal action.
     */
    public T3Action choose (T3State state) {
    	return alphabeta(state, Integer.MIN_VALUE, Integer.MAX_VALUE, true, null).action;
    }
    
    /**
     * 
     * @param state: current state of the game
     * @param alpha: minimal ability of the action
     * @param beta: maximal ability of the action
     * @param turn: represents whose turn it is
     * @param past: the previous most optimal action and score
     * @return actionScore of the most optimal first action and it's utility score
     */
    private actionScore alphabeta (T3State state, int alpha, int beta, boolean turn, actionScore past) {
    	if(state.isWin()) {
    		if(!turn) { 
			    return new actionScore(2, past.action);
    		}
    		if(turn) {
    			return new actionScore(0, past.action);	
				}
    	}
		if(state.isTie()) {
		    return new actionScore(1, past.action);
		}
    	if (turn) {
    		actionScore optimal = new actionScore(Integer.MIN_VALUE, null);
            Map<T3Action, T3State> transitions = state.getTransitions();
    		for(Map.Entry<T3Action, T3State> transition : transitions.entrySet()) {
    			actionScore child = alphabeta(transition.getValue(), alpha, beta, !turn, optimal);
    			if(transition.getValue().isWin()) {
    				optimal.action = transition.getKey();
    				optimal.score = child.score;
    				return optimal;
    			}
    			if(child.score > optimal.score) {
    				optimal.action = transition.getKey();
    				optimal.score = child.score;
    			}
    			alpha = Math.max(alpha, optimal.score);
    			if (beta <= alpha) {
    				break;
    			}
    		}
    		return optimal;
    	}
    	else {
    		actionScore optimal = new actionScore(Integer.MAX_VALUE, null);
    		Map<T3Action, T3State> transitions = state.getTransitions();
     		for(Map.Entry<T3Action, T3State> transition : transitions.entrySet()) {
    			actionScore child = alphabeta(transition.getValue(), alpha, beta, !turn, optimal);
    			if(transition.getValue().isWin()) {
    				optimal.action = transition.getKey();
    				optimal.score = child.score;
    				return optimal;
    			}
    			if(child.score < optimal.score) {
    				optimal.action = transition.getKey();
    				optimal.score = child.score;
    			}
    			beta = Math.min(beta, optimal.score);
    			if (beta <= alpha) {
    				break;
    			}
    		}
    		return optimal;
    	}
    }
    
    /**
     * 
     * actionScore class to pair the action with its corresponding utility score.
     *
     */
    class actionScore {
    	 int score;
    	 T3Action action;
    	 
    	/**
    	 * 
    	 * @param score: utility score of the action
    	 * @param action: possible T3action
    	 */
    	 actionScore(int score, T3Action action) {
    		this.score = score;
    		this.action = action;
    	}
    	
    }
}
     




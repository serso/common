package org.solovyev.common.utils.history;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.EqualsTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleHistoryHelper<T> implements HistoryHelper<T> {

	private static final int HISTORY_CAPACITY = 100;

	private List<T> history;
	
	private int currentStateIndex = -1;

	private int historyCapacity;

	public SimpleHistoryHelper() {
		this(HISTORY_CAPACITY);
	}

	public SimpleHistoryHelper(int historyCapacity) {
		this.historyCapacity = historyCapacity;
		this.history = new ArrayList<T> (historyCapacity);
	}

	@Override
	public T undo(@Nullable T currentState) {
		if ( !isUndoAvailable() ) { 
			throw new IndexOutOfBoundsException();
		}
		
		currentStateIndex--;
		
		return history.get(currentStateIndex);
	}

	@Override
	public T redo(@Nullable T currentState) {
		if (!isRedoAvailable()) {
			throw new IndexOutOfBoundsException();
		}
		currentStateIndex++;
		return history.get(currentStateIndex);
	}

	@Override
	public void addState(@Nullable T currentState) {
		if (needToAdd(currentState)) {
			if (currentStateIndex == history.size() - 1) {
				if (currentStateIndex < historyCapacity - 1) {
					currentStateIndex++;
					history.add(currentState);
				} else {
					history.remove(0);
					history.add(currentState);
				}
			} else {
				assert currentStateIndex < history.size() - 1 : "Invalid history state index!";
				currentStateIndex++;
				history.set(currentStateIndex, currentState);
				while( history.size() > currentStateIndex + 1 ) {
					history.remove(history.size() - 1);
				}
			}
		}
	}

	private boolean needToAdd(@Nullable T currentState) {
		boolean result;

		if ( history.isEmpty() ) {
			result = true;
		} else {
			result = !EqualsTool.areEqual( getLastHistoryState(), currentState);
		}

		return result;
	}

	@Override
	public boolean isEmpty() {
		return history.isEmpty();
	}

	@Override
	public T getLastHistoryState() {
		T result = null;

		if ( currentStateIndex >= 0 && currentStateIndex < history.size() ){
			result = history.get(currentStateIndex);
		}

		return result;
	}

	@Override
	public boolean isUndoAvailable() {
		return currentStateIndex > 0;
	}

	@Override
	public boolean isRedoAvailable() {
		return currentStateIndex < history.size() - 1;
	}

	@Override
	public boolean isActionAvailable(@NotNull HistoryAction historyAction) {
		boolean result = false;
		
		switch (historyAction) {
			case undo:
				result = isUndoAvailable();
				break;
			case redo:
				result = isRedoAvailable();
				break;
		}
		
		return result;
	}

	@Override
	public T doAction(@NotNull HistoryAction historyAction, @Nullable T currentState) {
		T result = null;
		
		switch (historyAction) {
			case undo:
				result = undo(currentState);
				break;
			case redo:
				result = redo(currentState);
				break;
		}
		
		return result;
	}

	@NotNull
	@Override
	public List<T> getStates() {
		return Collections.unmodifiableList(this.history);
	}

	@Override
	public void clear() {
		this.currentStateIndex = -1;
		this.history.clear();
	}
}

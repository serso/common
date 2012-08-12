package org.solovyev.common.history;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface HistoryHelper<T> {

	boolean isEmpty();

	@Nullable
	T getLastHistoryState();

	boolean isUndoAvailable();
	
	@Nullable
	T undo(@Nullable T currentState);
	
	boolean isRedoAvailable();
	
	@Nullable
	T redo(@Nullable T currentState);
	
	boolean isActionAvailable(@NotNull HistoryAction historyAction);
	
	@Nullable
	T doAction(@NotNull HistoryAction historyAction, @Nullable T currentState);
	
	void addState(@Nullable T currentState);

	@NotNull
	List<T> getStates();

	void clear();
}

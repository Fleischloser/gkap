package p3_abstract;

import java.util.List;

/*
 * Interface um den Ford Fulkerson und den Edmonds Karp in einer Implementierung zu ermöglichen
 * 
 */
public interface IStackQueue<E> extends List<E> {

	public E get();
	
}

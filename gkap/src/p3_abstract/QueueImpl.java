package p3_abstract;

import java.util.ArrayList;

public class QueueImpl<E> extends ArrayList<E> implements IStackQueue<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Liefert das erste Element in der Liste und löscht es
	 * 
	 */
	@Override
	public E get() {
		if (this.size() > 0) {
			E ret = this.get(0);
			
			this.remove(0);
			
			return ret;
		} else {
			return null;
		}
	}


}

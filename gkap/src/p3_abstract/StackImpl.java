package p3_abstract;

import java.util.ArrayList;

public class StackImpl<E> extends ArrayList<E> implements IStackQueue<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public E get() {
		if (this.size() > 0) {
			E ret = this.get(this.size() -1);
			
			this.remove(this.size() -1);
			
			return ret;
		} else {
			return null;
		}
	}

}

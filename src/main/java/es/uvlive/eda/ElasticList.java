package es.uvlive.eda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ElasticList<T> implements List<T> {
	
	private static final int MAX_SIZE = 10;
	private static final int BUFFER_SIZE = 2;
	
	private int size;
	private ElasticItem<T> firstElement;
	private ElasticItem<T> lastElement;
	
	private OnFillBufferCallback<T> onFillBufferCallback;
	
	public ElasticList(OnFillBufferCallback<T> onFillBufferCallback) {
		size = 0;
		this.onFillBufferCallback = onFillBufferCallback;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		ElasticItem<T> currentElement = firstElement;
		while (currentElement.getNextElement() != null) {
			if (currentElement.equals(o)) {
				return true;
			} else {
				currentElement = currentElement.getNextElement();
			}
		}
		return false;
	}

	@Override
	public boolean add(T e) {
		if (e != null) {
			ElasticItem<T> elasticItem = new ElasticItem<>(e);
			elasticItem.setNextElement(firstElement);
			if (firstElement != null) {
				firstElement.setPreviousElement(elasticItem);
			}
			
			if (lastElement == null) {
				lastElement = elasticItem;
			}
			size++;
			firstElement = elasticItem;
			checkAndSendBuffer();
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) {
			return Boolean.FALSE;
		}
		
		ElasticItem<T> currentElement = firstElement;
		while (currentElement.getNextElement() != null) {
			if (currentElement.equals(o)) {
				ElasticItem<T> previousElement = currentElement.getPreviousElement();
				ElasticItem<T> nextElement = currentElement.getNextElement();
				previousElement.setNextElement(nextElement);
				nextElement.setPreviousElement(previousElement);
				return Boolean.TRUE;
			}
			currentElement = currentElement.getNextElement();
		}
		return Boolean.FALSE;
	}

	@Override
	public void clear() {
		firstElement = null;
		lastElement = null;
	}

	@Override
	public T get(int index) {
		if (index >= 0 && index < size) {
			int i = 0;
			ElasticItem<T> currentElement = firstElement;
			while (currentElement.getNextElement() != null) {
				if (i == index) {
					return currentElement.getElement();
				}
				currentElement = currentElement.getNextElement();
			}
		}
		return null;
	}

	@Override
	public T remove(int index) {
		if (index >= 0 && index < size) {
			int i = 0;
			ElasticItem<T> currentElement = firstElement;
			while (currentElement.getNextElement() != null) {
				if (i == index) {
					ElasticItem<T> previousElement = currentElement.getPreviousElement();
					ElasticItem<T> nextElement = currentElement.getNextElement();
					previousElement.setNextElement(nextElement);
					nextElement.setPreviousElement(previousElement);
					return currentElement.getElement();
				}
				currentElement = currentElement.getNextElement();
			}
		}
		return null;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		ElasticItem<T> currentElement = firstElement;
		while (currentElement.getNextElement() != null) {
			if (currentElement.equals(o)) {
				return index;
			} else {
				currentElement = currentElement.getNextElement();
				index++;
			}
		}
		return -1;
	}
	
	private void checkAndSendBuffer() {
		if (size == MAX_SIZE) {
			ElasticItem<T> element = lastElement;
			int index = 0;
			ArrayList<T> elements = new ArrayList<>();
			
			while (element.getPreviousElement() != null && index<BUFFER_SIZE) {
				elements.add(element.getElement());
				element = element.getPreviousElement();
				index++;
			}
			
			element.setNextElement(null);
			lastElement = element;
			size-=BUFFER_SIZE;
			
			onFillBufferCallback.onBufferFilled(elements);
		}
	}
	
	public static interface OnFillBufferCallback<T> {
		void onBufferFilled(List<T> elements);
	}
	
	// Unimplemented methods
	@Override
	@Deprecated
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@Deprecated
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void add(int index, T element) {
		throw new UnsupportedOperationException();
		
	}
	
	@Override
	@Deprecated
	public Iterator<T> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public T[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	

	@Override
	@Deprecated
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
}

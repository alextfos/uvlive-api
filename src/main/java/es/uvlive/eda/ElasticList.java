package es.uvlive.eda;

import java.util.*;
/*
* Uso: la primera carga se realiza mediante el método addAll para cargar los mensajes provinientes de la BD
* A partir de este momento se sincronizarán solamente los mensajes nuevos que no esten ya en la BD
*
* */
public class ElasticList<T> implements List<T> {

	private static final int NO_POSITION = -1;
	private int maxSize;
	private int bufferSize;
	private int size;

	private ElasticItem<T> firstElement;
	private ElasticItem<T> lastElement;
	private ElasticItem<T> lastSync;
	
	private OnFillBufferCallback<T> onFillBufferCallback;
	
	public ElasticList(int maxSize, int bufferSize, OnFillBufferCallback<T> onFillBufferCallback) {
		size = 0;
		this.maxSize = maxSize;
		this.bufferSize = bufferSize;
		this.onFillBufferCallback = onFillBufferCallback;
	}

	public T getFirstElement() {
		return firstElement.getElement();
	}

	public T getLastElement() {
		return lastElement.getElement();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		if (c == null) {
			return Boolean.TRUE;
		}
		for (T e:c) {
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
			} else {
				return Boolean.FALSE;
			}
		}
		lastSync = firstElement;
		return Boolean.TRUE;
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
		return NO_POSITION;
	}
	
	private void checkAndSendBuffer() {
		if (size >= maxSize) {
			ElasticItem<T> element = lastElement;
			int index = 0;
			ArrayList<T> elements = new ArrayList<>();
			
			while (element.getPreviousElement() != null && index<bufferSize) {
				if (lastSync == null) {
					elements.add(element.getElement());
				}
				if (element.equals(lastSync)) {
					lastSync = null;
				}
				element = element.getPreviousElement();
				index++;
			}
			
			element.setNextElement(null);
			lastElement = element;
			size-=bufferSize;

			if(elements.size()>0) {
				onFillBufferCallback.onBufferFilled(elements);
			}
		}
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex>=size) {
			return null;
		}
		ArrayList<T> subList = new ArrayList<>();

		ElasticItem<T> element = firstElement;
		for (int index=0 ; element.getNextElement() != null && index != fromIndex ; index++) {
			element = element.getNextElement();
		}
		
		for (int i=fromIndex ; i<=toIndex && element !=null ; i++) {
			subList.add(element.getElement());
			element = element.getNextElement();
		}
		return subList;
	}
	
	@Override
	public String toString() {
		String txt = "";
		ElasticItem<T> currentElement = firstElement;
		txt += currentElement.getElement().toString()+"\t\n";
		while (currentElement.getNextElement() != null) {
			currentElement = currentElement.getNextElement();
			txt += currentElement.getElement().toString()+"\t\n";
		}
		return txt;
	}
	
	public interface OnFillBufferCallback<T> {
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
}

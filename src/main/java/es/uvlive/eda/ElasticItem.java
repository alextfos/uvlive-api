package es.uvlive.eda;

public class ElasticItem<T> {
	private T element;
	private ElasticItem<T> nextElement;
	private ElasticItem<T> previousElement;
	
	public ElasticItem(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}

	public ElasticItem<T> getNextElement() {
		return nextElement;
	}
	
	public void setNextElement(ElasticItem<T> nextElement) {
		this.nextElement = nextElement;
	}
	
	public ElasticItem<T> getPreviousElement() {
		return previousElement;
	}
	
	public void setPreviousElement(ElasticItem<T> previousElement) {
		this.previousElement = previousElement;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null) {
			try {
				return ((T)o).equals(element);
			} catch (Exception e) {
				//If it produces class cast exception we return false
			}
		}		
		return false;
	}
	
}

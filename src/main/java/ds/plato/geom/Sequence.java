package ds.plato.geom;

import java.util.Iterator;

import org.apache.commons.lang3.Range;

public class Sequence<T extends Number> implements Iterable<T> {
	
	Range<T> range;
	T increment;

	public Sequence(Range<T> range, T increment) {
		this.range = range;
		this.increment = increment;
	}

	@Override
	public Iterator<T> iterator() {
		return new SequenceIterator(range, increment);
	}

	private class SequenceIterator implements Iterator<T> {
		
		Range<T> range;
		Number increment;
		Number currentValue;

		public SequenceIterator(Range<T> range, T increment) {
			this.range = range;
			currentValue = range.getMinimum();
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Method 'SequenceIterator.hasNext' not yet implemented");
		}

		@Override
		public T next() {
			//return (T) (currentValue.doubleValue() + increment.doubleValue());
			throw new UnsupportedOperationException("Method 'Sequence.SequenceIterator.next' not yet implemented");
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Method 'SequenceIterator.remove' not yet implemented");
		}

	}

}

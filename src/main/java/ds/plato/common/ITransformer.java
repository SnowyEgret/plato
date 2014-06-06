package ds.plato.common;

import ds.plato.select.Selection;


public interface ITransformer<S> {
	public Selection transform(Selection selection);
}

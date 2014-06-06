package ds.plato.spell.transform;

import ds.plato.select.Selection;


public interface ITransform<S> {
	public Selection transform(Selection selection);
}

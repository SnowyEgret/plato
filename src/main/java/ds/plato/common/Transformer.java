package ds.plato.common;


public interface Transformer<S> {
	public Selection transform(Selection selection);
}

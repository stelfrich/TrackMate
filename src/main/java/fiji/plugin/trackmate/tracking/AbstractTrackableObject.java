package fiji.plugin.trackmate.tracking;

import java.util.concurrent.atomic.AtomicInteger;

import net.imglib2.AbstractRealLocalizable;

public abstract class AbstractTrackableObject< T extends AbstractTrackableObject< T >> extends AbstractRealLocalizable
		implements TrackableObject< T >
{

	public static AtomicInteger IDcounter = new AtomicInteger( -1 );

	/** A user-supplied name for this object. */
	protected String name;

	protected final int id;

	private final int frame;

	public AbstractTrackableObject( final double[] position, final int id,
			final int frame )
	{
		this( position, null, id, frame );
	}

	public AbstractTrackableObject( final double[] position, final int frame )
	{
		this( position, null, IDcounter.incrementAndGet(), frame );
	}

	public AbstractTrackableObject( final double[] position, final String name,
			final int frame )
	{
		this( position, name, IDcounter.incrementAndGet(), frame );
	}

	public AbstractTrackableObject( final double[] position, final String name,
			final int id, final int frame )
	{
		super( position );
		this.id = id;
		this.frame = frame;

		synchronized ( IDcounter )
		{
			if ( IDcounter.get() < id )
			{
				IDcounter.set( id );
			}
		}

		if ( name == null )
		{
			this.name = "ID" + id;
		}
		else
		{
			this.name = name;
		}

	}

	/**
	 * @return the name for this Object.
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * Set the name of this Object.
	 */
	@Override
	public void setName( final String name )
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		return ID();
	}

	@Override
	public boolean equals( final Object other )
	{
		if ( other == null )
			return false;
		if ( other == this )
			return true;
		if ( !( other instanceof AbstractTrackableObject ) )
			return false;
		@SuppressWarnings( "rawtypes" )
		final AbstractTrackableObject os = ( AbstractTrackableObject ) other;
		return os.ID() == this.id;
	}

	@Override
	public String toString()
	{
		String str;
		if ( null == name || name.equals( "" ) )
			str = "ID" + ID();
		else
			str = name;
		return str;
	}

	@Override
	public int ID()
	{
		return id;
	}

	@Override
	public int frame()
	{
		return frame;
	}

	@Override
	public int compareTo( final T o )
	{
		return id - o.id;
	}

}

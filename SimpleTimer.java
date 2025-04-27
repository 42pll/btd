public class SimpleTimer
{
    private long lastMark = System.currentTimeMillis();
    public void mark()
    {
        lastMark = System.currentTimeMillis();
    }
    public int millisElapsed()
    {
        return (int) (System.currentTimeMillis() - lastMark);
    }
}

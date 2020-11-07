package IntObjMapIml;

import java.util.Arrays;

public class IntObjMapIml<V> implements IntObjectMap {

    /***
     * _count is an array that keeps track on the number of each bucket
     * _taken keeps track on the number of slot that is taken
     * _keys is an array keeps track on the keys for the Map
     * _values is an array keeps the values for the Map
     * _length is the total capacity of the Map
     * _nullKey is the key that represents the slot is not used
     */
    private int[]     _count;
    private int       _taken;
    private int[]     _keys;
    private Object[]  _values;
    private int       _length;
    private final int _nullKey = Integer.MIN_VALUE;

    /***
     * Constructor
     * The initial length is 32
     */
    public IntObjMapIml(){
        _length = 32;
        _taken  = 0;
        _count  = new int[_length];
        _keys   = new int[_length];
        _values = new Object[_length];

        // fill the array with default values
        Arrays.fill(_keys, _nullKey);
    } // test_f9xi7y

    /**
     * A method called when there is only half of the space left for the Map
     * It creates new arrays and put the original keys and values into the new array
     */
    private void newMap(){
        int      tempLength    = _length;
        int[]    tempKeys      = _keys;
        Object[] tempValues    = _values;

        // make the length of the arrays two times larger
        _length *= 2;
        _count   = new int[_length];
        _keys    = new int[_length];
        _values  = new Object[_length];

        // make _taken 0
        // because the put method called later
        // will fill in the number
        _taken   = 0;

        Arrays.fill(_keys, _nullKey);

        // put all the key and value pair into our new arrays
        for (int i = 0; i < tempLength; i++){
            if (tempKeys[i] != _nullKey){
               put(tempKeys[i], tempValues[i]);
            }
        }

    }

    /**
     * This method is used to generate hashCode for key
     * When the key is positive, this will return the module of
     * the key with _length
     * When the key is negative, this will return the module of
     * the key with _length plus the _length to guarantee the
     * hashCode is positive
     *
     * @param key
     * @return hasCode for the key
     */
    private int idxHash(int key){
        return (key % _length < 0)? (key % _length) + _length : key % _length;
    }

    /**
     * The put method add a specific key and value pair to the map
     * If the key was in the map already, replace the value
     * correspond to the key by the input value
     *
     * @param key the key to add to the map
     * @param value the value corresponding to the key
     * @return the previous value correspond to the key or null
     */
    @Override
    public V put(int key, Object value){

        if (key == _nullKey || value == null){
            throw new IllegalArgumentException("The key is the nullKey or the value is null!");
        }

        // if there is less than half the space,
        // make the space twice by calling newMap()
        if(_taken > _length / 2){
            newMap();
        }

        var hashIdx = idxHash(key);
        var idx = hashIdx;

        // This is the case of having the key already,
        // replace the value by the new value
        // no count change, no taken change
        while(_keys[idx]    != _nullKey){
            if (_keys[idx]  == key){
                var temp     = _values[idx];
                _values[idx] = value;
                return (V) temp;
            }
            idx = ++idx % _length;
        }

        // the key has not been taken before,
        // put the key and value in the array
        // add the count and the taken by one
        _count[hashIdx]++;
        _keys[idx]   = key;
        _values[idx] = value;
        _taken++;
        return null;
    } // test_f9xi7y

    /**
     * Returns the value corresponding to the specified key.
     *
     * @param key the key
     * @return the value corresponding to the specified key or null otherwise
     */
    @Override
    public V get(int key) {

        // throw an exception if the key is _nullKey
        if (key == _nullKey){
            throw new IllegalArgumentException("The key cannot be the nullKey");
        }

        // find the hashCode for the key to locate the index
        var hash        = idxHash(key);
        var numElements = _count[hash];
        var idx         = hash;

        // loop over the index to find the key
        while(_keys[idx] != key){
            // use numElements to prevent infinite loop
            // whenever we go through an index matches
            // the hashCode, we eliminate one potential
            // target to look at
            if (_keys[idx] % _length == hash){numElements--;}
            // if we look at all index with the hashCode
            // but can't find the key, we return null
            if (numElements <= 0){return null;}
            idx = ++idx % _length;
        }
        // return the value after we find the key
        return (V) _values[idx];
    } // test_f9xi7y

    /**
     * Removes and returns the value corresponding to the specified key.
     *
     * @param key the key
     * @return the value corresponding to the key or null
     */
    @Override
    public V remove(int key) {

        // throw an exception if the key is _nullKey
        if (key == _nullKey){
            throw new IllegalArgumentException("The key cannot be the nullKey");
        }

        // First find the key
        // the first part is similar to
        // the get method
        var hash        = idxHash(key);
        var numElements = _count[hash];
        var idx         = hash;

        while(_keys[idx] != key){
            if (_keys[idx] % _length == hash){numElements--;}
            if (numElements <= 0){return null;}
            idx = ++idx % _length;
        }

        // Once the key is found
        // save the value for
        // later return
        // Then move the rest of the
        // keys and values pairs
        // with the same HashCode
        // up to fill the empty space
        var temp = _values[idx];
        var prev = idx;
        // One less count
        _count[hash]--;
        // One less taken
        _taken--;

        numElements--;
        // Loop and move the later
        // elements to fill in the
        // empty space
        while (numElements > 0){
            idx = ++idx % _length;
            // Only move the elements
            // with the same HashCode
            if (_keys[idx] % _length == hash){
                _keys[prev]   = _keys[idx];
                _values[prev] = _values[idx];
                prev          = idx;
                numElements--;
            }
        }

        // Set the last element to null
        _keys[prev]   = _nullKey;
        _values[prev] = null;

        return (V) temp;
    } // test_f9xi7y

    /**
     * Returns the number of elements in the map.
     *
     * @return the number of elements in the map
     */
    @Override
    public int size() {
        return _taken;
    } // test_f9xi7y

    /**
     * Removes all the elements from this map.
     */
    @Override
    public void clear() {
        _taken = 0;
        Arrays.fill(_count, 0);
        Arrays.fill(_keys, _nullKey);
        Arrays.fill(_values, null);
    } // test_f9xi7y
}

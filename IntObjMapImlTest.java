package IntObjMapIml;

import org.junit.jupiter.api.Test;

class IntObjMapImlTest {

    @Test
    void test_f9xi7y() {

        // Set up a flag for testing
        // IllegalArgument Exception
        int flag = 0;
        // Set up a IntObjMapIml Object for testing
        IntObjMapIml<String> sMap = new IntObjMapIml<String>();
        // Testing putting an illegal argument in the Map
        try{
            sMap.put(Integer.MIN_VALUE, "wouldn't work");
        } catch (Exception e){
            flag ++;
        }
        // Asserting that the Exception works
        assert(flag == 1);

        // Putting a bunch of keys
        // and values into the map
        sMap.put(1, "1");
        sMap.put(2, "2");
        sMap.put(33, "33");
        sMap.put(3, "3");
        sMap.put(34, "34");
        sMap.put(0, "0");

        // Testing the Exception
        // for the get method
        try{
            sMap.get(Integer.MIN_VALUE);
        } catch (Exception e){
            flag ++;
        }

        assert (flag == 2);

        // Testing the get method correctly
        assert (sMap.get(-1) == null);
        assert (sMap.get(0)  == "0");
        assert (sMap.get(1)  == "1");
        assert (sMap.get(2)  == "2");
        assert (sMap.get(3)  == "3");
        assert (sMap.get(33) == "33");
        assert (sMap.get(34) == "34");

        // Testing the size method
        assert (sMap.size()  == 6);

        // Testing the remove method
        assert (sMap.remove(2) == "2");
        assert (sMap.get(2)         == null);
        assert (sMap.size()         == 5);
        assert (sMap.get(34)        == "34");
        assert (sMap.get(0)         == "0");
        assert (sMap.get(1)         == "1");
        assert (sMap.get(3)         == "3");
        assert (sMap.get(33)        == "33");

        // Testing the enlarge of the map
        for (int i = 40; i < 100; i++){
            sMap.put(i, "testy test");
        }
        assert (sMap.get(34) == "34");
        assert (sMap.get(0)  == "0");
        assert (sMap.get(1)  == "1");
        assert (sMap.get(3)  == "3");
        assert (sMap.get(33) == "33");
        for (int i = 40; i < 100; i++){
            assert (sMap.get(i) == "testy test");
        }
        assert (sMap.size() == 65);

        // Testing the clear method
        sMap.clear();

        assert (sMap.size() == 0);
        assert (sMap.get(34) == null);
        assert (sMap.get(0)  == null);
        assert (sMap.get(1)  == null);
        assert (sMap.get(3)  == null);
        assert (sMap.get(33) == null);
        for (int i = 40; i < 100; i++){
            assert (sMap.get(i) == null);
        }

        // Testing negative key
        sMap.put(-34, "42f");
        assert (sMap.get(-34) == "42f");
    }
}
### Attention
This project is still in development, so some features may not work properly. Also there is no config for dbs' credentials, yet.

### Introduction
This is a simple to use data storage library, which uses MongoDB as a storage and Redis/Local cache. 

### Creating Data Manager and its object
First of all we need to create our Object class (with all basic annotations), which have to extend DataObject 
class and specify the type of the key:
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@BsonDiscriminator 
public class TestObject extends DataObject<String> {
    
    private int level;
    
}
```

Then we need to create our DataManager class, which have to extend DataManager class and again specify the type of the key:
```java
public class TestObjectDataManager extends DataManager<TestObject, String> {

    public TestObjectDataManager() {
        super("databaseName", false); // set 2nd parameter to true if you want to use Redis cache
    }

    // provide the object with default values
    @Override
    protected TestObject getTemplate() {
        return new TestObject();
    }
}
```


### Working with Data Managers
Here's an example of how to use the DataManager class and its methods:
```java
TestObjectDataManager testObjectDataManager = new TestObjectDataManager(this);
testObjectDataManager.load("test"); // load object from storage to cache
testObjectDataManager.save("test"); // save object from cache to storage
testObjectDataManager.unload("test"); // unload object from cache
testObjectDataManager.unloadAll(); // unload all objects from cache

TestObject object = testObjectDataManager.get("test"); // get object from cache
Map<String, TestObject> allStoredObjects = testObjectDataManager.getStorage(); // get all objects from storage
Map<String, TestObject> allCachedObjects = testObjectDataManager.getCache(); // get all objects from cache
```

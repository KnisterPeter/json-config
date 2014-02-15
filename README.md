json-config is an OSGi service extending felix fileinstall with the option
to create ConfigurationAdmin entries from json files.

This json will be converted...

    {
      "some": "test",
      "array": [1, 2, 3],
      "array2": [
        {"a":"b"},
        {"c":"d"}
      ],
      "object": {
        "key1": "value1",
        "key2": "value2"
      }
    }

... to this config entries:

    some=test
    array.0=1
    array.1=2
    array.2=3
    array2.0.a=b
    array2.1.c=d
    object.key1=value1
    object.key2=value2

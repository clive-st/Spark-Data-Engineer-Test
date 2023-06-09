# General Data Engineer Tech Test

## requirements
tested with jvm 11 
using [skdman](https://sdkman.io/)  `sdk use java 11.0.8-amzn`

## Command input
--input s3a://xxx/* --output s3a://xxxx/result_tsv --awsprofile xxxx

using sbt `sbt "run --input s3a://xxx/* --output s3a://xxxx/result_tsv --awsprofile xxxx"`

## Algo
for each file, we group by key, then for each key with sort the values.
We traverse the values keeping only odd values (even + x = x => we do not need them).
When we reduce all the files, 2 by 2, we keep the uncommon keys, and for the common ones,
we keep the difference ( key1: a.values.diff(b.values.diff)) as if we have
2 odds = even, our odd can only be present one time. 
complexity, nlog(n)

## Error handling
to improve, we could detect in the reduce operation if we have more than one odd value for each index,
and also detect in a key does not have any odd value.


## testing
simple unit test for each part of the system. 

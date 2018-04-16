# Crunch
Fast string compression/decompression for large text input with a constant memory footprint.

## Usage

```
Crunch crunch = new Crunch.Builder()
       // configure
       .build();

// in-memory string
String compressed = crunch.compress("abc");
String original = crunch.decompress(compressed);

// stream file
File compressed = crunch.compress(file, compressedDestination);
File decompressed = crunch.decrompress(compressed, decompressedDestination);
```
or in the comand line
```
usage: crunch
    --compress <text>                 Compress source, either from a file
                                      or text
    --decompress                      Decompress source from a file
    --destinationFile <destination>   File directory
    --help                            Help
    --searchBufferSize <bufferSize>   Use the same bufferSize to compress
                                      and decompress
    --sourceFile <sourceFile>         File directory
```

## Install
```
implementation 'com.giljulio.crunch:crunch:0.1.0'
```
## License
```
Copyright 2018 Gil Sinclair-Julio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
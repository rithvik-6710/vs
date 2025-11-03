const { Duplex } = require('stream');

const myStream = new Duplex({
  write(chunk, encoding, callback) {
    console.log('Writing:', chunk.toString());
    callback();
  },
  read(size) {
    this.push('Hello from Duplex Stream!\n');
    this.push(null);
  }
});

myStream.write('Input Data\n');
myStream.on('data', chunk => console.log('Reading:', chunk.toString()));
myStream.end();

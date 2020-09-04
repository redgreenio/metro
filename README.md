# Metro

### Bytecode Quick Reference
The following table has the list of opcodes that we are currently interested in. The complete reference can be found [here](https://en.wikipedia.org/wiki/Java_bytecode_instruction_listings). 

| Mnemonic          | Opcode (Hex)   | Stack <br/> [before] → [after]          |
|-------------------|----------------|-----------------------------------------|
| `getfield`        | `b4`           | `objectref → value`                     |
| `putfield`        | `b5`           | `objectref, value → `                   |
| `invokevirtual`   | `b6`           | `objectref, [arg1, arg2, ...] → result` |
| `invokespecial`   | `b7`           | `objectref, [arg1, arg2, ...] → result` |
| `invokestatic`    | `b8`           | `[arg1, arg2, ...] → result`            |
| `invokeinterface` | `b9`           | `[arg1, arg2, ...] → result`            |
| `invokedynamic`   | `ba`           | `[arg1, arg2, ...] → result`            |

```
© Copyright 2020 Red Green, Inc.
```

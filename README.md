# ISO 8583

> ISO 8583 is an international standard for financial transaction card originated interchange messaging. It is the International Organization for Standardization standard for systems that exchange electronic transactions initiated by cardholders using payment cards.

> ISO 8583 defines a message format and a communication flow so that different systems can exchange these transaction requests and responses. The vast majority of transactions made when a customer uses a card to make a payment in a store (EFTPOS) use ISO 8583 at some point in the communication chain, as do transactions made at ATMs. In particular, the MasterCard, Visa and Verve networks base their authorization communications on the ISO 8583 standard, as do many other institutions and networks.


## Message format

An ISO 8583 message is made of the following parts:

- Message type indicator (MTI)
- One or more bitmaps, indicating which data elements are present
- Data elements, the actual information fields of the message

### Message type indicator (MTI)


**The first digit of the MTI indicates** the ISO 8583 version in which the message is encoded

| Code  | Meaning |
| :---  | :---    |
|0xxx	|ISO 8583:1987|
|1xxx	|ISO 8583:1993|
|2xxx	|ISO 8583:2003|
|3xxx	|Reserved by ISO|
|4xxx   | |
|5xxx   | |
|6xxx   | |
|7xxx   | |
|8xxx	|National use|
|9xxx	|Private use|

**Position two of the MTI specifies** the overall purpose of the message.Position two of the MTI specifies the overall purpose of the message.

|Code	|Meaning	                        |Usage              |
| :---  | :---                              | :---              |
|x0xx	|Reserved by ISO                    | - |	
|x1xx	|Authorization message              | Determine if funds are available, get an approval but do not post to account for reconciliation. Dual message system (DMS), awaits file exchange for posting to the account. |
|x2xx	|Financial messages                 | Determine if funds are available, get an approval and post directly to the account. Single message system (SMS), no file exchange after this. |
|x3xx	|File actions message               | Used for hot-card, TMS and other exchanges |
|x4xx	|Reversal and chargeback messages   | Reversal (x4x0 or x4x1): Reverses the action of a previous authorization. Chargeback (x4x2 or x4x3): Charges back a previously cleared financial message. |
|x5xx	|Reconciliation message             | Transmits settlement information message. |
|x6xx	|Administrative message             | Transmits administrative advice. Often used for failure messages (e.g., message reject or failure to apply). |
|x7xx	|Fee collection messages            | - |
|x8xx	|Network management message         |	Used for secure key exchange, logon, echo test and other network functions.|
|x9xx	|Reserved by ISO                    | - |

**Position three of the MTI specifies** the message function which defines how the message should flow within the system. Requests are end-to-end messages (e.g., from acquirer to issuer and back with time-outs and automatic reversals in place), while advices are point-to-point messages (e.g., from terminal to acquirer, from acquirer to network, from network to issuer, with transmission guaranteed over each link, but not necessarily immediately).


|Code	|Meaning                        | Notes|
| :---   | :---                         | :--- |
|xx0x	|Request                        | Request from acquirer to issuer to carry out an action; issuer may accept or reject|
|xx1x	|Request response               | Issuer response to a request|
|xx2x	|Advice                         | Advice that an action has taken place; receiver can only accept, not reject|
|xx3x	|Advice response                | Response to an advice|
|xx4x	|Notification                   | Notification that an event has taken place; receiver can only accept, not reject|
|xx5x	|Notification acknowledgement   |Response to a notification|
|xx6x	|Instruction                    | ISO 8583:2003|
|xx7x	|Instruction acknowledgement    | - |
|xx8x	|Reserved for ISO use           | Some implementations (such as MasterCard) use for positive acknowledgment.[4]|
|xx9x	|Some implementations           | (such as MasterCard) use for negative acknowledgement.[5]|

**Position four of the MTI defines** the location of the message source within the payment chain.

|Code	|Meaning            |
| :---  | :---              |
|xxx0	|Acquirer           |
|xxx1	|Acquirer repeat    |
|xxx2	|Issuer             |
|xxx3	|Issuer repeat      |
|xxx4	|Other              |
|xxx5	|Other repeat       |
|xxx6	|Reserved by ISO    |
|xxx7   | -                 |
|xxx8   | -                 |
|xxx9   | -                 |

# word-chain

Problem:

Given a list of words, construct a chain of words where each word in the chain has each of the previous word’s letters, plus one more. For example:

no
one
tone
teton

...would be a chain of four words. Note that the letter order does not matter. In ‘no’, the n is before the o. In ‘one’, the o is before the n.

Solution:

Group words based on size

Derive a key for word so that lookup efficiency is realized

Use Levenshtein distance algorithm to find differences between pair of words

Use a stack to build chain by recursively traversing thru groups of words

Instructions: 

$ mvn package

Usage: java -jar target/word-miner.jar [url] [size] where url is provider of word list and size is desired word chain size

$ java -jar target/word-miner.jar http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt 10

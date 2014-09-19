LibLib
======

A tiny but robust madlib parser.

Basic Usage
===========

Using this is super easy. This is how you write a basic lib:

    He is %adjective%. His favorite object is the %noun%.
    
And when the text file containing that is read, upon reaching %adjective%, the user is prompted with this:

    Enter an adjective: 
    
And then the user enters in one word (multiple words can be entered but only the first one will be digested).

Let's say for the adjective the user enters 'awesome,' and for the noun they enter 'HashMap.' When the parser reaches
the end of the file, it prints the lines with the new words in place. So, the output would be this:

    He is awesome. His favorite object is the HashMap.
    
You not limited to such general word types like that. For example, a lib could be written like this:

    His favorite animal is the %animal%.
  
And the parser will prompt the user:

    Enter an animal:
    
The prompt also checks if the word begins with a vowel and adjusts the 'a' to 'an' as demonstrated above.

References
==========

As words are entered and replaced, each word is stored in an internal map object. This enables the user to easily reference 
previously used words without having to prompt the user again. The syntax for references is as follows:

    %$wordtype%
    
The only difference between this and a regular placeholder is the presence of a dollar sign after the first percent sign.

Each value stored in the map is just the word type without the surrounding characters, so the above example would be stored
with the key *wordtype*. Consequently, you can put anything you want as **wordtype** and make a reference to it later, but
make sure you're using the correct case and spelling.

Here's an example about how a reference is used:

    He is %adjective%. Because he is so %$adjective%, he quite likes %noun%.
    
The prompts the user would recieve are:

    Enter an adjective:
  
    Enter a noun:
  
Assuming the input for the adjective is 'lame' and the input for the noun is 'C#,' this is what the output would be:

    He is lame. Because he is so lame, he quite likes C#.
  
For conveinence, it's also possible to number your references' internal representations while having no effect on how the user
is prompted. For example:

    She is %adjective-1%. He is %adjective-2%.
  
The only way it is guaranteed to work correctly is if you follow the format **%wordtype-#%**. Naturally, the syntax to make
a reference like this still remains the same:

    She is %adjective-1%. Hey! I SAID SHE'S %$adjective-1%!!
  
In the case of an unresolved reference (these are when the parser tries to acquire a value from the map but none is present)
a ParseException is thrown. This is mainly a utility for the authors.

Other notes
===========

Due to the quick way files are loaded into the program, newlines aren't naturally inserted. As a result, you have to break them
yourself using **$n**. Example:

    This is one line, but after this $n it is split into two.
  
Ensure there's a space before and after the **$n** or else it will just become a part of the word it's attached to instead
of being recognized as a symbol.

Happy libbing!

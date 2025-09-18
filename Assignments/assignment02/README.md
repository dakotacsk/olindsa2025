# Reverse the Elements in a Stack

This is the strategy for the solution:

### Problem
How would you reverse the elements in a stack (i.e., put the elements at the top of the stack on the bottom and vice 
versa)? You can use as many additional stacks and queues as temporary storage in your approach.

### Solution

**Clarifying Questions**
- Should the reversal be done in place (reuse the original stack object) or is returning a new reversed stack acceptable?
- Any constraints on n (stack size) or on space complexity?

**Space/Time:**
Time: O(n) (each element is popped once, enqueued once, dequeued once, pushed once; constants aside this is linear).
Extra space: O(n) for the queue.

**Psuedocode:**

1. Create an empty queue Q.

2. While S is not empty:
   1. Pop the top element from S.
   2. Enqueue that element into Q.

3. While Q is not empty:
   1. Dequeue the front element from Q.
   2. Push that element onto S.

Now S is reversed.

---

## Valid Parentheses Problem

This is the strategy for the solution. I actually did this problem like 2 months ago in Python.

### Problem

Given a string `s` containing just the characters `(`, `)`, `{`, `}`, `[` and `]`, determine if the input string is valid.

An input string is valid if:

1.  Open brackets must be closed by the same type of brackets.
2.  Open brackets must be closed in the correct order.
3.  Every close bracket has a corresponding open bracket of the same type.

### Solution

Using a stack data structure. The psuedocode is as follows:

1.  Create a map to store the matching pairs of brackets. The open brackets will be the keys and the close brackets will be the values.
2.  Iterate through the input string character by character.
3.  If the character is an open bracket, push it onto the stack.
4.  If the character is a close bracket, check if the stack is empty. If it is, the string is invalid.
5.  If the stack is not empty, pop the top element from the stack and check if it matches the current close bracket. If it does not, the string is invalid.
6.  After iterating through the entire string, if the stack is empty, the string is valid. Otherwise, it is invalid.

### Implementation

The solution is implemented in the `Main.kt` file!

---

## Copy the Elements in a Stack

This is the strategy for the solution:

### Problem

Given a stack, return a **copy** of the original stack (i.e., a new stack with the same values as the original, stored in the same order as the original). You may use one queue as auxiliary storage.

### Solution

**Clarifying Questions**

- Should the original stack remain unchanged? (Usually yes.)
- Can we assume the stack supports standard `push`, `pop`, `isEmpty`, and `peek` operations?
- Is it acceptable to use only one queue for auxiliary storage? (Yes, by problem statement.)

**Space/Time:**

- Time: **O(n)** — each element is popped once, enqueued once, dequeued once, and pushed twice.
- Space: **O(n)** for the queue + new stack.

**Pseudocode:**

We want to copy stack `S` into a new stack `C`:

1. Create an empty queue `Q`.

2. While `S` is not empty:
   1. Pop the top element from `S`. 
   2. Enqueue that element into `Q`.

3. While `Q` is not empty:
   1. Dequeue the front element from `Q`.
   2. Push it onto `S` (to restore original). 
   3. Push it onto `C` (to build copy).

4. Now `S` has its original order back, and `C` is a copy of `S`.
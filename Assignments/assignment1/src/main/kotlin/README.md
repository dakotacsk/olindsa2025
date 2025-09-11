# Assignment 1: Hello Kotlin and Getting to Know You
**Author: Dakota Chang**

**Date Last Edited: 06 Sept 2025**


- What features do you like about Kotlin?

I really liked the map. I think it's a very neat way to organise information and very intuitive compared to Python
dictionaries. The "when" logic is also very cool, I don't find myself using the "switch" condition a lot but this makes
me excited to use it.

- Are there things you were expecting to find that you haven’t?

Not really actually, I think it covers the basics of what we would need. I'd like to know more about the libraries and
whether it has things like queues built in eventually though.

- What questions do you have?

I think I need a refresher on object-oriented languages because the order of which things are executed is getting a
little confusing for me. I'm super used to Python right now. Another question I have is not very important, but I'm
curious as to why they call the functions in classes "member." It makes sense, but I've never seen that before.

- Try using the debugger (see the Getting Set with Kotlin page) for some very basic information on the debugger. Do you have experience using interactive debuggers like this one? Were you able to successfully launch the debugger?

I like the Python interactive debugger a lot, so I have some experience with it. The debugger here is very intuitive and
very helpful to me debugging my recursive function.


The old code is linked in a private space. I'll just include it here for reference.
```{python}
'''
Today, you will implementing the "paint bucket" question, also called Flood Fill. This is a common operation in the manipulation of [raster images](https://en.wikipedia.org/wiki/Raster_graphics). In a flood fill operation, a color is selected, then a location in the image is chosen. The pixel at that location is changed to the selected color, along with all locations connected to it via horizontal or vertical connections. For example if the numbers represent colors in the following matrix:

1  1  1
1  0  1
0  1  0

If we select color 5 and location 0, 0 (the top left corner), then the result will be:

<space />

5  5  5
5  0  5
0  1  0


input
4  5  5
5  0  5
0  1  0

sr = 0, sc = 0
select color 4

Notice that the 1 on the last row remains because it is not connected to group of 1s selected.

This problem is available on Leetcode here: https://leetcode.com/problems/flood-fill/
 

EXAMPLE(S)
None
 

FUNCTION SIGNATURE
function floodFill(image, sr, sc, newColor)
def flood_fill(image, sr, sc, new_color):


Clarifying questions
- 2D array
- sr/sc can be out of bounds or not -> no


Look at the starting pixel [sr, sc] - old color
if old color is the same as new color -> return image

helper fill(row, column)
    location of pixel is out of bounds -> stop
    if pixel is not the old color -> stop
    otherwise change pixel color to new color
    then continue to fill/call fill for 
        up
        down
        left
        right

fill(sr,sc)
return the updated image


'''


def flood_fill(image, sr, sc, new_color):
    rows, cols = len(image), len(image[0])
    old_color = image[sr][sc]

    if old_color == new_color:
        return image

    def fill(r, c):
        if r < 0 or r >= rows or c < 0 or c >= cols:
            return
        if image[r][c] != old_color:
            return
        
        image[r][c] = new_color

        fill(r - 1, c) # up
        fill(r + 1, c) # down
        fill(r, c - 1) # left
        fill(r, c + 1) # right

    fill(sr, sc)
    return image

# test

# 1  1  1
# 1  0  1
# 0  1  0
image = [[1,1,1], [1,0,1], [0,1,0]]
print(flood_fill(image, 0, 0, 5))

# 4  1  1
# 1  0  1
# 0  1  0
image = [[4,1,1], [1,0,1], [0,1,0]]
print(flood_fill(image, 0, 0, 5))

# 1  1  1
# 1  1  1
# 1  1  1
image = [[1,1,1], [1,1,1], [1,1,1]]
print(flood_fill(image, 0, 0, 5))

# 0  1  0
# 1  1  1
# 0  1  0
image = [[0,1,0], [1,1,1], [0,1,0]]
print(flood_fill(image, 1, 1, 5))

# 1  4  1
# 3  2  1
# 1  1  1
image = [[1,4,1], [3,2,1], [1,1,1]]
print(flood_fill(image, 0, 0, 5))
```
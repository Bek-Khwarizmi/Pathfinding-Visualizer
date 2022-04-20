# Pathfinding Visualizer

![city-map-route-starting-point-destination-vetor-cartoon-ilustration-109592754](https://user-images.githubusercontent.com/95633808/164272255-ecd99739-754e-4bc8-9757-e0f8f3074086.jpg)

## Index :pushpin:
- [About](#about) 
- [How to use](#use)
- [See it in action](#action)

## About <a name="about"></a> :bulb:

Pathfinding Visualizer is a desktop application. It demonstrates the flow of the algorithms that are used to find the path from start to target in 2D grid. Where
- :blue_square: indicates start node
- :red_square: indicates target node
- :white_large_square: indicates unvisited node(s)
- :green_square: indicates visited node(s)
- :yellow_square: indicates path node(s)
- :black_large_square: indicates wall node(s)

:heavy_check_mark: We can use the following four algorithms to search path (Press **W** to speed up the algorithm, press **S** to do reverse):
- Breadth-first search
- Depth-first search
- A star Search
- Minimum path finder

:heavy_check_mark: Also we can generate borders before search begins. We can do it in two ways:
- By generating borders randomly
- By generating borders recursivels

:zap: There is a new feature - we can choose directions: each cell has 8 neighbour cells, and we can choose which cells can be visited which are not. Choosing directions is important because if we choose top, right, bottom and left (firts case) then algorithm can cover the board while searching, although if we choose diogonal ones (second case) then most (~ 50%) cells cannot be reached so there might not be a path:

:blush: **First case:** ![pathnotfound](https://user-images.githubusercontent.com/95633808/164271598-82eddcff-d956-4eb6-a07d-3ad090e2b455.png)

:frowning_face: **Second case** ![notfound](https://user-images.githubusercontent.com/95633808/164272907-e80017ff-abf5-44e0-b94a-69ba846e8d49.png)

## How to use <a name="use"></a> :question:

First we need to choose directions. As explained earlier this step is very important. Then we can add randomly generated borders or recursively generated maze. To do this we need to pick one and then press **Generate** button. Now choose an algorithm and press **Visualize** button. Press **Reset** button to restart the process. Enjoy!

## See it in action <a name="action"></a> :cinema:

https://user-images.githubusercontent.com/95633808/164254345-88caa4de-ce3b-4b5b-8e38-c2d775b1bbd2.mp4

class Graph:
    def __init__(self):
        self._vertices = dict()  # vertices=dict( set, set) first set used for inbound, second for outbound
        self._edges = dict()  # we store edges in a dict: key = (starting point-ending point) and value - the cost

    @property
    def vertices(self):
        return self._vertices

    @property
    def edges(self):
        return self._edges

    def init_vertices(self, n):
        for i in range(n):
            self._vertices[i] = (set(), set())  # first set for inbound, second for outbound

    def get_number_of_vertices(self):
        return len(self._vertices)

    def get_number_of_edges(self):
        return len(self._edges)

    def parse_vertices(self):
        """
        Parse the vertices and return the list that contains all vertices
        """
        return self._vertices.keys()

    def check_if_edge(self, start, end):
        """
        We look for the endpoint in the outbound set from the key of the starting point
        We return 1 if the endpoint is found, which means that startpoint and endpoint form an edge, 0 otherwise
        """
        if end in self.vertices[start][1]:
            return 1
        return 0

    def get_in_degree_of_vertex(self, vertex):
        """
        We return  the number of values of the inbound set from the key indicated by the given vertex
        """
        return len(self._vertices[vertex][0])

    def get_out_degree_of_vertex(self, vertex):
        """
        We return  the number of values of the outbound set from the key indicated by the given vertex
        """
        return len(self._vertices[vertex][1])

    def parse_in_specific_vertex(self, vertex):
        """
        We store in a list the values of the inbound set found at the key 'vertex'
        We return the list
        """
        returning_list = list()
        for inn in self._vertices[vertex][0]:
            returning_list.append(inn)

        return returning_list

    def parse_out_specific_vertex(self, vertex):
        """
        We store in a list the values of the outbound set found at the key 'vertex'
        We return the list
        """
        returning_list = list()
        for out in self._vertices[vertex][1]:
            returning_list.append(out)

        return returning_list

    def modify_edge_cost(self, x, y, new_cost):
        """
        In the dict of edges, the structure of the key is : x-y, where x represents the starting point and y the
        ending point. Firstly, we check if the given vertices form an edge and if they do, we form the told structure
        (x-y) and modify the cost corresponding to that key. Also, we return 1 if the update was successfully made, 0
        otherwise.
        """
        if self.check_if_edge(x, y):
            edge = str(x) + "-" + str(y)
            self._edges[edge] = new_cost
            return 1
        return 0

    def retrieve_edge_cost(self, x, y):
        """
        We verify if x and y form an edge and if it does, the function returns the cost of that specific edge, -1
        otherwise
        """
        if self.check_if_edge(x, y):
            edge = str(x) + "-" + str(y)
            return self._edges[edge]
        return -1

    def add_edge(self, start, end, cost):
        """
        Adds a new edge between two existing vertices which do not form an edge, or not, if they do not exist or
        they already form an edge
        """
        if self.check_if_edge(start, end) == 1:
            raise ValueError("Edge", start, "-", end, "was already added!")

        self._vertices[start][1].add(end)
        self._vertices[end][0].add(start)
        key = str(start) + "-" + str(end)
        self._edges[key] = cost

    def remove_edge(self, start, end):
        """
        :param start: the starting point
        :param end:  the ending point
        :return: none
        """
        if self.check_if_edge(start, end) == 0:
            raise ValueError("The edge does not exist!")
        else:
            self._vertices[start][1].remove(end)
            self._vertices[end][0].remove(start)
            edge = str(start) + "-" + str(end)
            self._edges.pop(edge)

    def add_vertex(self, vertex):
        """
        We initialise a new vertex
        :param vertex: The vertex to be added
        :return: none
        """
        if self._vertices[vertex][0] is not None or self._vertices[vertex][1] is not None:
            raise ValueError("The vertex already exists!")
        self._vertices[vertex] = (set(), set())

    def remove_vertex(self, vertex):
        """
        Removes a vertex
        :param vertex: the vertex to be removed
        :return: none
        """
        pop_list = list()

        for line in self._edges.keys():
            x, y = line.split(maxsplit=1, sep="-")

            if int(x) == int(vertex):
                pop_list.append(line)
                if vertex in self._vertices[y][1]:
                    self._vertices[y][1].remove(vertex)
                if vertex in self._vertices[y][0]:
                    self._vertices[y][0].remove(vertex)

            elif int(y) == int(vertex):
                pop_list.append(line)
                if vertex in self._vertices[x][1]:
                    self._vertices[x][1].remove(vertex)
                if vertex in self._vertices[x][0]:
                    self._vertices[x][0].remove(vertex)
            else:
                raise ValueError("The vertex does not exist!")

        self._vertices.pop(vertex)
        for x in pop_list:
            self._edges.pop(x)

    def copy_graph(self):
        """
        :return: A copy of the graph
        """
        return self

from Graph import Graph


class UI:
    def __init__(self):
        self._graph = Graph()

    @staticmethod
    def menu(self):
        print("0.Exit\n"
              "1. Number of vertices\n"
              "2. Set of vertices\n"
              "3. Find edge between 2 vertices\n"
              "4. Get in and out degree\n"
              "5. Set of outbound edges\n"
              "6. Set of inbound edges\n"
              "7. Retrieve an edge\n"
              "8. Modify an edge information\n"
              "9. Add edge\n"
              "10. Remove edge\n"
              "11. Add vertex\n"
              "12. Remove vertex\n"
              "13. Generate random graph\n"
              "14. Copy graph\n")

    def start(self):
        ok = False

        while not ok:
            file_name = input("Enter the file you want to read from: ")
            ok = self.read_file(file_name)

        try:
            while True:
                print()
                self.menu(self)
                self.save_to_file()

                command = int(input("Input an option: "))
                if command == 1:
                    print(self._graph.get_number_of_vertices())
                elif command == 2:
                    self._show_vertices()
                elif command == 3:
                    self._is_edge()
                elif command == 4:
                    self._get_in_out_degree()
                elif command == 5:
                    self._outbound_edges()
                elif command == 6:
                    self._inbound_edges()
                elif command == 7:
                    self._retrieve_edge_cost()
                elif command == 8:
                    self._modify_edge_cost()
                elif command == 9:
                    self._add_edge()
                elif command == 10:
                    self._remove_edge()
                elif command == 11:
                    self._add_vertex()
                elif command == 12:
                    self._remove_vertex()
                elif command == 13:
                    pass
                elif command == 14:
                    copy = self._copy_graph()
                elif command == 0:
                    break
                else:
                    print("Invalid option!")
        except ValueError as ve:
            print(str(ve))

    def read_file(self, file_name):
        try:
            f = open(file_name, "r")
            line = f.readline()
            nodes, edges_count = line.split(maxsplit=1, sep=" ")
            self._graph.init_vertices(int(nodes))
            for line in f.readlines():
                if len(line) > 0:
                    line = line.strip()
                    start, end, cost = line.split(maxsplit=2, sep=" ")
                    start, end, cost = int(start), int(end), int(cost)
                    self._graph.vertices[start][1].add(end)
                    self._graph.vertices[end][0].add(start)
                    edge = str(start) + "-" + str(end)
                    self._graph.edges[edge] = cost
            f.close()
            return True
        except IOError:
            print("Error reading!")

    def save_to_file(self):
        try:
            f = open("output.txt", "w")
            for key in self._graph.edges.keys():
                x, y = key.split(maxsplit=1, sep="-")
                cost = self._graph.edges[key]
                f.write(str(x) + " " + str(y) + " " + str(cost) + "\n")

            f.close()
        except IOError:
            print("Error saving to file!")

    def _show_vertices(self):
        n = self._graph.get_number_of_vertices()
        for vertex in range(n):
            print(vertex)

    def _is_edge(self):
        x = int(input("Enter the starting point: "))
        y = int(input("Enter the ending point: "))
        found = self._graph.check_if_edge(x, y)
        if found:
            print("The edge between " + str(x) + " and " + str(y) + " exists")
        else:
            print("The edge between " + str(x) + " and " + str(y) + " does not exist")

    def _get_in_out_degree(self):
        vertex = int(input("Enter a vertex: "))
        print("In degree: ", self._graph.get_in_degree_of_vertex(vertex))
        print("Out degree: ", self._graph.get_out_degree_of_vertex(vertex))

    def _outbound_edges(self):
        vertex = int(input("Enter a vertex:"))
        print_list = self._graph.parse_out_specific_vertex(vertex)
        print_list = sorted(print_list)
        for vertex in print_list:
            print(vertex)

    def _inbound_edges(self):
        vertex = int(input("Enter a vertex:"))
        print_list = self._graph.parse_in_specific_vertex(vertex)
        print_list = sorted(print_list)
        for vertex in print_list:
            print(vertex)

    def _retrieve_edge_cost(self):
        x = int(input("Enter the starting point: "))
        y = int(input("Enter the ending point: "))
        cost = self._graph.retrieve_edge_cost(x, y)
        if cost == -1:
            print("The edge between " + str(x) + " and " + str(y) + " does not exist.")
        else:
            print("The cost of the edge between " + str(x) + " and " + str(y) + " is " + str(cost))

    def _modify_edge_cost(self):
        x = int(input("Enter the starting point: "))
        y = int(input("Enter the ending point: "))
        new_cost = int(input("Enter the new cost of the edge: "))
        cost = self._graph.modify_edge_cost(x, y, new_cost)
        if cost == 1:
            print("The update was done successfully.")
        else:
            print("The edge does not exist.")

    def _add_edge(self):
        start = int(input("Starting vertex: "))
        end = int(input("Ending vertex: "))
        cost = int(input("The cost of the edge: "))
        try:
            self._graph.add_edge(start, end, cost)
        except ValueError as e:
            print(e)

    def _remove_edge(self):
        start = int(input("Starting vertex: "))
        end = int(input("Ending vertex: "))
        try:
            self._graph.remove_edge(start, end)
        except ValueError as e:
            print(e)

    def _add_vertex(self):
        try:
            vertex = int(input("Enter vertex: "))
            self._graph.add_vertex(vertex)
        except ValueError as e:
            print(e)

    def _remove_vertex(self):
        try:
            vertex = int(input("Enter vertex: "))
            self._graph.remove_vertex(vertex)
        except ValueError as e:
            print(e)

    def _copy_graph(self):
        copy_graph = self._graph.copy_graph()
        return copy_graph


ui = UI()
ui.start()

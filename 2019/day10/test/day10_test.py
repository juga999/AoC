import unittest
import math
import itertools

def dot_product(v1, v2):
    return v1[0]*v2[0] + v1[1]*v2[1] 

def cross_product(v1, v2):
    return v1[0]*v2[1] - v1[1]*v2[0]

def have_same_direction(v1, v2):
    return cross_product(v1, v2) == 0 and dot_product(v1, v2) > 0

def vector_length(v):
    return math.sqrt(v[0] ** 2 + v[1] ** 2)

class Day10Test(unittest.TestCase):

    def setUp(self):
        self.asteroids = []

        self.asteroids.append((1,0))
        self.asteroids.append((4,0))

        self.asteroids.append((0,2))
        self.asteroids.append((1,2))
        self.asteroids.append((3,2))
        self.asteroids.append((4,2))

        self.asteroids.append((4,3))

        self.asteroids.append((3,4))
        self.asteroids.append((4,4))

    def test_detection(self):
        in_sight = {}
        from_asteroid = (4,2)
        for other in self.asteroids:
            if other == from_asteroid:
                continue
            dx = from_asteroid[0] - other[0]
            dy = from_asteroid[1] - other[1]
            in_sight[other] = (dx, dy)

        tmp = in_sight.copy()
        for o1, o2 in itertools.product(tmp.items(), tmp.items()):
            other1 = o1[0]
            other2 = o2[0]
            d1 = o1[1]
            d2 = o2[1]
            if d1 == d2:
                continue
            if have_same_direction(d1, d2):
                len1 = vector_length(d1)
                len2 = vector_length(d2)
                if len1 < len2:
                    in_sight.pop(other2, None)
                else:
                    in_sight.pop(other1, None)

        self.assertEqual(len(in_sight), 5)


if __name__ == '__main__':
    unittest.main()

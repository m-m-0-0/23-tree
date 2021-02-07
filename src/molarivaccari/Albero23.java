package molarivaccari;

import movida.commons.Movie;

public class Albero23 {
    Movie m1;
    Movie m2;
    Albero23 parent;
    Albero23 left;
    Albero23 center;
    Albero23 right;
    Albero23 temp;
    int curr_i; //usato per visita

    public Albero23(Albero23 parent) {
        this.parent = parent;
        curr_i = 0;
    }

    public int size() {
        int c = 0;
        if (isLeaf())
            return 1;

        c += left.size();
        if (center != null)
            c += center.size();
        if (right != null)
            c += right.size();

        return c;

    }

    public Movie[] getAllMovies() {
        Movie[] M = new Movie[size()];
        visitToArray(M);
        curr_i = 0;
        return M;
    }

    private void visitToArray(Movie[] M) {
        if (isLeaf())
            M[getRoot().curr_i++] = m1;
        else {
            left.visitToArray(M);
            if (center != null)
                center.visitToArray(M);
            if (right != null)
                right.visitToArray(M);
        }
    }

    private Albero23 getRoot(Albero23 T) {
        if (T.parent == null)
            return T;
        else
            return getRoot(T.parent);
    }

    public Movie getMax() {
        if (isLeaf())
            return m1;

        if (right != null)
            return right.getMax();
        if (center != null)
            return center.getMax();
        else
            return left.getMax();
    }

    private void updateMax(Albero23 child, Movie m) {
        if (child == left)
            m1 = m;
        if (child == center)
            m2 = m;
        if (parent != null)
            parent.updateMax(this, m);
    }

    private void split() {
        Albero23 N = new Albero23(parent);
        N.setM1(right.getMax());
        N.setLeft(right);
        right.setParent(N);
        N.setM2(temp.getMax());
        N.setCenter(temp);
        temp.setParent(N);
        right = null;
        temp = null;
        if (parent == null) {
            parent = new Albero23(null);
            N.setParent(parent);
            parent.setM1(center.getMax());
            parent.setLeft(this);
            parent.setCenter(N);
        } else if (parent.getCenter() == null) {
            parent.setCenter(N);
            updateMax(N, N.getMax());
        } else if (parent.getRight() == null) {
            parent.setRight(N);
            updateMax(N, N.getMax());
        } else {
            parent.setTemp(N);
            parent.split();
        }
    }

    private void setParent(Albero23 parent) {
        this.parent = parent;
    }

    public Integer insert(Movie m) {
        if (left.isLeaf() & center.isLeaf() && (right == null || right.isLeaf())) {
            if (right == null) {
                if (m.compareTo(m1) < 0) {
                    right = center;
                    center = left;
                    left = new Albero23(this);
                    left.setM1(m);
                    m2 = m1;
                    m1 = m;
                } else if (m.compareTo(m2) < 0) {
                    right = center;
                    center = new Albero23(this);
                    center.setM1(m);
                    m2 = m;
                } else if (m.compareTo(m2) > 0) {
                    right = new Albero23(this);
                    right.setM1(m);
                    updateMax(this, m); //parent.
                }
            } else {
                if (m.compareTo(m1) < 0) {
                    temp = right;
                    right = center;
                    center = left;
                    left = new Albero23(this);
                    left.setM1(m);
                    m2 = m1;
                    m1 = m;
                } else if (m.compareTo(m2) < 0) {
                    temp = right;
                    right = center;
                    center = new Albero23(this);
                    center.setM1(m);
                    m2 = m;
                } else if (m.compareTo(m2) > 0) {
                    if (m.compareTo(right.getM1()) < 0) {
                        temp = right;
                        right = new Albero23(this);
                        right.setM1(m);
                        //parent.updateMax(this, m);
                    } else {
                        temp = new Albero23(this);
                        temp.setM1(m);
                        updateMax(this, m);
                    }
                }
                split();
            }
        } else {
            if (m.compareTo(m1) < 0)
                left.insert(m);
            else if (right == null || m.compareTo(m2) < 0)
                center.insert(m);
            else if (m.compareTo(m2) > 0 && right != null)
                right.insert(m);
        }
        return 0;
    }

    public Integer addMovie(Movie m) {
        if (left == null) {
            left = new Albero23(this);
            left.setM1(m);
            m1 = m;
        } else if (center == null) {
            Albero23 N = new Albero23(this);
            N.setM1(m);
            if (m.compareTo(m1) > 0) {
                center = N;
                m2 = m;
            } else {
                center = left;
                left = N;
                m2 = m1;
                m1 = m;
            }
        } else {
            insert(m);
        }
        return 0;
    }

    public int deleteMovie(String title) {
        if (this == getRoot() && center == null) {
            if (left.getM1().getTitle().compareTo(title) == 0) {
                left = null;
            } else
                return -1;
        } else if (left.isLeaf() && center.isLeaf() && (right == null || right.isLeaf())) {
            if (right != null) {
                if (title.compareTo(left.getM1().getTitle()) == 0) {
                    left = center;
                    center = right;
                    right = null;
                    m1 = left.getM1();
                    m2 = center.getM1();
                    updateMax(this, m2);
                } else if (title.compareTo(center.getM1().getTitle()) == 0) {
                    center = right;
                    right = null;
                    m2 = center.getM1();
                    updateMax(this, m2);
                } else if (title.compareTo(right.getM1().getTitle()) == 0) {
                    right = null;
                } else
                    return -1;
            } else {
                if (this == getRoot()) {
                    if (title.compareTo(left.getM1().getTitle()) == 0) {
                        left = center;
                        center = null;
                        m1 = left.getMax();
                    } else if (title.compareTo(center.getM1().getTitle()) == 0) {
                        center = null;
                        m2 = null;
                    } else
                        return -1;
                } else {
                    boolean deleted = false;
                    if (this == parent.getLeft()) {
                        if (parent.getCenter().getRight() != null) {
                            int check = deleteAndStealRight(title, parent.getCenter());
                            if (check == -1)
                                return -1;
                            deleted = true;
                        }
                    } else if (this == parent.getCenter()) {
                        if (parent.getLeft().getRight() != null) {
                            int check = deleteAndStealLeft(title, parent.getLeft());
                            if (check == -1)
                                return -1;
                            deleted = true;
                        } else if (parent.getRight() != null && parent.getRight().getRight() != null) {
                            int check = deleteAndStealRight(title, parent.getRight());
                            if (check == -1)
                                return -1;
                            deleted = true;
                        }
                    } else if (parent.getRight() != null && this == parent.getRight()) {
                        if (parent.getCenter().getRight() != null) {
                            int check = deleteAndStealLeft(title, parent.getCenter());
                            if (check == -1)
                                return -1;
                            deleted = true;
                        }
                    }
                    if (!deleted) {
                        if (title.compareTo(left.getM1().getTitle()) == 0) {
                            left = center;
                            center = null;
                        }
                        else if (title.compareTo(center.getM1().getTitle()) == 0)
                            center = null;
                        else
                            return -1;

                        if (this == parent.getLeft()) {
                            moveToRight(parent.getCenter());
                        } else if (this == parent.getCenter() && parent.getLeft().getRight() == null) {
                            moveToLeft(parent.getLeft());
                        } else if (this == parent.getCenter() && parent.getRight().getRight() == null) {
                            moveToRight(parent.getRight());
                        } else if (this == parent.getRight()) {
                            moveToLeft(parent.getCenter());
                        }

                        parent.fixTree(this);
                    }
                }
            }
        } else {
            if (title.compareTo(m1.getTitle()) <= 0)
                left.deleteMovie(title);
            else if (right == null || title.compareTo(m2.getTitle()) <= 0)
                center.deleteMovie(title);
            else if (title.compareTo(m2.getTitle()) > 0 && right != null)
                right.deleteMovie(title);
        }
        return 0;
    }

    private int deleteAndStealRight(String title, Albero23 sib) {
        if (title.compareTo(left.getM1().getTitle()) == 0)
            left = center;
        else if (title.compareTo(center.getM1().getTitle()) == 0)
            center = null;
        else
            return -1;

        center = sib.getLeft();
        center.setParent(this);
        m2 = center.getMax();
        sib.setLeft(sib.getCenter());
        sib.setM1(sib.getLeft().getMax());
        sib.setCenter(sib.getRight());
        sib.setM2(sib.getCenter().getMax());
        sib.setRight(null);
        parent.updateMax(sib, sib.getMax());
        parent.updateMax(this, m2);
        return 0;
    }

    private void deleteAndStealRight(Albero23 node, Albero23 sib) {
        if (node == left)
            left = center;
        else if (node == center)
            center = null;

        center = sib.getLeft();
        center.setParent(this);
        m2 = center.getMax();
        sib.setLeft(sib.getCenter());
        sib.setM1(sib.getLeft().getMax());
        sib.setCenter(sib.getRight());
        sib.setM2(sib.getCenter().getMax());
        sib.setRight(null);
        parent.updateMax(sib, sib.getMax());
        parent.updateMax(this, m2);
    }

    private void deleteAndStealLeft(Albero23 node, Albero23 sib) {
        if (node == left)
            left = center;
        else if (node == right)
            center = null;

        center = left;
        m2 = center.getMax();
        left = sib.getRight();
        sib.setRight(null);
        left.setParent(this);
        m1 = left.getMax();
        updateMax(this, m2);
    }

    private int deleteAndStealLeft(String title, Albero23 sib) {
        if (title.compareTo(left.getM1().getTitle()) == 0)
            left = center;
        else if (title.compareTo(center.getM1().getTitle()) == 0)
            center = null;
        else
            return -1;

        center = left;
        m2 = center.getMax();
        left = sib.getRight();
        sib.setRight(null);
        left.setParent(this);
        m1 = left.getMax();
        updateMax(this, m2);
        return 0;
    }

    private void moveToRight(Albero23 sib) {
        sib.setRight(sib.getCenter());
        sib.setCenter(sib.getLeft());
        sib.setLeft(left);
        left.setParent(sib);
        left = null;
        sib.setM1(sib.getLeft().getMax());
        sib.setM2(sib.getCenter().getMax());
        parent.updateMax(sib, sib.getM2());
    }

    private void moveToLeft(Albero23 sib) {
        sib.setRight(left);
        left.setParent(sib);
        left = null;
        parent.updateMax(sib, sib.getRight().getMax());
    }

    private int fixTree(Albero23 child) {
        if(this == getRoot() && center == child) {
            m1 = left.getM1();
            m2 = left.getM2();
            center = left.getCenter();
            center.setParent(this);
            right = left.getRight();
            right.setParent(this);
            left = left.getLeft();
            left.setParent(this);
        } else if (right != null) {
            if (child == left) {
                left = center;
                center = right;
                right = null;
                m1 = left.getMax();
                m2 = center.getMax();
                updateMax(this, m2);
            } else if (child == center) {
                center = right;
                m2 = center.getMax();
                right = null;
                updateMax(this, m2);
            } else if (child == right){
                right = null;
            }
        } else {
            if (this == getRoot()) {
                if (child == left) {
                    left = center;
                    m1 = left.getMax();
                    center = null;
                    m2 = null;
                } else if (child == center) {
                    center = null;
                    m2 = null;
                } else
                    return -1;
            } else {
                boolean deleted = false;
                if (this == parent.getLeft()) {
                    if (parent.getCenter().getRight() != null) {
                        deleteAndStealRight(child, parent.getCenter());
                        deleted = true;
                    }
                } else if (this == parent.getCenter()) {
                    if (parent.getLeft().getRight() != null) {
                        deleteAndStealLeft(child, parent.getLeft());
                        deleted = true;
                    } else if (parent.getRight() != null && parent.getRight().getRight() != null) {
                        deleteAndStealRight(child, parent.getRight());
                        deleted = true;
                    }
                } else if (parent.getRight() != null && this == parent.getRight()) {
                    if (parent.getCenter().getRight() != null) {
                        deleteAndStealLeft(child, parent.getCenter());
                        deleted = true;
                    }
                }

                if (!deleted) {
                    if (child == left) {
                        left = center;
                        center = null;
                    }
                    else if (child == center)
                        center = null;
                    else
                        return -1;

                    if (this == parent.getLeft())
                        moveToRight(parent.getCenter());
                    else if (this == parent.getCenter() && parent.getLeft().getRight() == null)
                        moveToLeft(parent.getLeft());
                    else if (this == parent.getCenter() && parent.getRight().getRight() == null)
                        moveToRight(parent.getRight());
                    else if (this == parent.getRight())
                        moveToLeft(parent.getCenter());

                    parent.fixTree(this);
                }
            }
        }
        return 0;
    }


    public int type() {
        if (right != null)
            return 2;
        else
            return 3;
    }

    public boolean isEmpty() {
        return getM1() == null && getM2() == null;
    }

    public boolean isLeaf() {
        return left == null;
    }

    public Movie getM1() {
        return m1;
    }

    public Movie getM2() {
        return m2;
    }

    public Albero23 getLeft() {
        return left;
    }

    public Albero23 getRight() {
        return right;
    }

    public Albero23 getTemp(){
        return temp;
    }

    public Albero23 getCenter() {
        return center;
    }

    private void setM1(Movie m1) {
        this.m1 = m1;
    }

    private void setM2(Movie m2) {
        this.m2 = m2;
    }

    private void setLeft(Albero23 left) {
        this.left = left;
    }

    private void setCenter(Albero23 center) {
        this.center = center;
    }

    private void setRight(Albero23 right) {
        this.right = right;
    }

    private void setTemp(Albero23 temp) {
        this.temp = temp;
    }

    public Albero23 getRoot() {
        if(parent == null)
            return this;
        else
            return parent.getRoot();
    }
}

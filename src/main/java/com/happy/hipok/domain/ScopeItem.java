package com.happy.hipok.domain;

/**
 * Item of the scope.
 */
public class ScopeItem {

    private Publication publication;
    private long nbComments;
    private long nbShares;

    public ScopeItem(Publication publication, long nbComments, long nbShares) {
        this.publication = publication;
        this.nbComments = nbComments;
        this.nbShares = nbShares;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public long getNbComments() {
        return nbComments;
    }

    public void setNbComments(long nbComments) {
        this.nbComments = nbComments;
    }

    public long getNbShares() {
        return nbShares;
    }

    public void setNbShares(long nbShares) {
        this.nbShares = nbShares;
    }
}


package com.example.draldo.retrofitapi.modules;

import java.util.ArrayList;
import java.util.List;

public class Result {

    public Integer page;
    public List<Movies> results = new ArrayList<Movies>();
    public Integer totalResults;
    public Integer totalPages;

}

package com.example.firstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class home_qst extends Fragment {
    View view2;
    private RecyclerView blog_list_View;
    //Create list of model class type
    private List<QuestionPost> blogList;
    private DocumentSnapshot lastVisible;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mauth;
    private QstRecycleAdapter blogRecycleAdapter;
    private boolean firstPageLoaded = true;




    public home_qst() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view2= inflater.inflate(R.layout.fragment_home_qst, container, false);
        blog_list_View = view2.findViewById(R.id.blog_list_view);
        blogList = new ArrayList<>();
        blogRecycleAdapter = new QstRecycleAdapter(blogList);

        //Set adapter to Recycler View
        blog_list_View.setLayoutManager(new LinearLayoutManager(getActivity()));
        blog_list_View.setAdapter(blogRecycleAdapter);
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();
            mauth = FirebaseAuth.getInstance();

            //Get Last item Scrolled in REcyclerView
            blog_list_View.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    boolean lastItem = !recyclerView.canScrollVertically(1);

                    if (lastItem) {
//                Toast.makeText(getContext(),"end of 3 posts",Toast.LENGTH_SHORT).show();
                        loadMorePosts();
                    }
                }
            });

            if (mauth.getCurrentUser() != null) {
                //Order according to date
                Query firstQuery = firebaseFirestore.collection("Posts")
                        .orderBy("timeStamp", Query.Direction.DESCENDING)
                        .limit(3);
                //getActivity bcoz to stop the on scroll listener after page closed bcause it will still call load more post
                firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {

                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                        //get lastVisibile iff first page not loaded at starting
                        if (firstPageLoaded) {
                            // Get the last visible documentSnapshot
                            lastVisible = documentSnapshots.getDocuments()
                                    .get(documentSnapshots.size() - 1);
                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                //Blog Id ..name same as that is Extender class
                                String BlogPostId = doc.getDocument().getId();
                                //USE MODEL CLASS and save one object obtained into Model class list
                                QuestionPost blogPost = doc.getDocument().toObject(QuestionPost.class).withId(BlogPostId);


                                if (firstPageLoaded) {
                                    blogList.add(blogPost);
                                }
                                //Add new post to top
                                else {
                                    blogList.add(0, blogPost);
                                }


                                blogRecycleAdapter.notifyDataSetChanged();
                            }
                        }

                    }


                });


            }


    }
        return view2;
    }
    private void loadMorePosts() {

        if (mauth.getCurrentUser() != null) {
            Query nextQuery = firebaseFirestore.collection("Posts")
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .startAfter(lastVisible)
                    .limit(3);

            nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    //If no more posts than docSnaps will be empty leading to crash
                    if (!documentSnapshots.isEmpty()) {

                        // Get the last visible documentSnapshot
                        lastVisible = documentSnapshots.getDocuments()
                                .get(documentSnapshots.size() - 1);


                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                //USE MODEL CLASS and save one object obtained into Model class list
                                String BlogPostId = doc.getDocument().getId();
                                //USE MODEL CLASS and save one object obtained into Model class list
                                QuestionPost blogPost = doc.getDocument().toObject(QuestionPost.class).withId(BlogPostId);
                                blogList.add(blogPost);

                                blogRecycleAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                }
            });
        }
    }
}
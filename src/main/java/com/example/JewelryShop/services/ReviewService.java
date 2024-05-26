package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.JewelryItemDTO;
import com.example.JewelryShop.dtos.ReviewDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Customer;
import com.example.JewelryShop.models.Image;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.models.Review;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JewelryItemRepository jewelryItemRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private JewelryItemService jewelryItemService;

    @Transactional
    public ResponseEntity<?> addNewReview(ReviewDTO reviewDTO, List<MultipartFile> images) {
        Customer customer = customerRepository.findById(reviewDTO.getCustomer_id()).orElseThrow(() -> new NotFoundException("Customer with id " + reviewDTO.getCustomer_id() + " not found"));
        JewelryItem jewelryItem = jewelryItemRepository.findById(reviewDTO.getJewelry_item_id()).orElseThrow(() -> new NotFoundException("JewelryItem with id " + reviewDTO.getJewelry_item_id() + " not found"));
        Review review = reviewDTO.toEntity();
        review.setCustomer(customer);
        review.setJewelry_item(jewelryItem);
        if (images != null) {
            for (MultipartFile image : images) {
                Image newImage = new Image();
                newImage.setUrl(cloudinaryService.upload(image));
                newImage.setReview(review);
                review.getImages().add(newImage);
            }
        }
        jewelryItem.getReviews().add(review);
        reviewRepository.save(review);
        jewelryItemService.updateJewelryRating(jewelryItem);
        return ResponseEntity.ok().body("Review added successfully");
    }

    public List<Review> getReviewsByJewelryItemId(Long jewelryItemId) {
        return reviewRepository.findByJewelryItemId(jewelryItemId);
    }

    public ResponseEntity<?> deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review with id " + reviewId + " not found"));
        review.setIs_deleted(true);
        reviewRepository.save(review);
        return ResponseEntity.ok().body("Review deleted successfully");
    }

    public ResponseEntity<?> updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        review.setText(reviewDTO.getText());
        review.setRating(reviewDTO.getRating());
        reviewRepository.save(review);
        return ResponseEntity.ok().body("Review updated successfully");
    }
}

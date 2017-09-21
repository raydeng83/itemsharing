package com.itemsharing.service.impl;

import com.itemsharing.event.SimpleSourceBean;
import com.itemsharing.model.Role;
import com.itemsharing.model.User;
import com.itemsharing.model.UserRole;
import com.itemsharing.repository.RoleRepository;
import com.itemsharing.repository.UserRepository;
import com.itemsharing.service.UserService;
import com.itemsharing.utility.SecurityUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by z00382545 on 9/19/17.
 */

@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private SimpleSourceBean simpleSourceBean;

//    @Autowired
//    private AmazonS3 amazonS3;

//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;

    @Override
    public User createUser(User user) {
        User localUser = userRepository.findByUsername(user.getUsername());

        simpleSourceBean.publishUserChange("CREATE", user.getUsername());

        if(localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {

            Set<UserRole> userRoles = new HashSet<>();
            Role localRole = new Role();
            localRole.setRoleId(1);
            userRoles.add(new UserRole(user, localRole));
            user.getUserRoles().addAll(userRoles);

            Date today = new Date();
            user.setJoinDate(today);

            String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);
            localUser = userRepository.save(user);


//            simpleSourceBean.publishUserChange("CREATE", localUser.getUsername());

//            createS3UserFolder(user);


        }

        return localUser;
    }

//    private void createS3UserFolder(User user) {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(0);
//
//        // create empty content
//        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
//
//        // create a PutObjectRequest passing the folder name suffixed by /
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,
//                user.getUsername()+"_"+user.getId()+"/init_0", emptyContent, metadata);
//
//        // send request to S3 to create folder
//        amazonS3.putObject(putObjectRequest);
//    }

    @Override
    public User getUserByUsername(String username) {
//        simpleSourceBean.publishUserChange("FIND_USER_BY_USERNAME", username);

        return userRepository.findByUsername(username);
    }
}

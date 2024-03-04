package com.danglich.jobxinseeker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@GetMapping("/")
	public String showHome(Model theModel) {
		
		 // Lấy danh sách tin tuyển dụng từ nguồn dữ liệu (ví dụ: cơ sở dữ liệu)
        List<Job> jobs = getJobsFromDataSource();

        // Đưa danh sách tin tuyển dụng vào model để sử dụng trong template
        theModel.addAttribute("jobs", jobs);
        
        return "index";
	}
	
	private List<Job> getJobsFromDataSource() {
        // Lấy danh sách tin tuyển dụng từ nguồn dữ liệu (ví dụ: cơ sở dữ liệu)
        // Đây chỉ là một ví dụ đơn giản, bạn cần thay thế bằng cách lấy dữ liệu thực tế
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty TNHH Smart - IT solution", "Lương 1", "Địa chỉ 1"));
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty 2", "Lương 2", "Địa chỉ 2"));
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty 3", "Lương 3", "Địa chỉ 3"));
		jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty TNHH Smart - IT solution", "Lương 1", "Địa chỉ 1"));
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty 2", "Lương 2", "Địa chỉ 2"));
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty 3", "Lương 3", "Địa chỉ 3"));
		jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty TNHH Smart - IT solution", "Lương 1", "Địa chỉ 1"));
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty 2", "Lương 2", "Địa chỉ 2"));
        jobs.add(new Job("https://cdn-new.topcv.vn/unsafe/80x/https://static.topcv.vn/company_logos/MwlF6XfJGPbWvKCcqdsLBbgnkRoD9n2j_1645605190____3fedde2900b0d89c963f8e389a3d6656.jpg", "Thực tập sinh lập trình java - không yêu cầu kình nghiệm", "Công ty 3", "Lương 3", "Địa chỉ 3"));
        return jobs;
    }

    // Class đại diện cho một tin tuyển dụng
    public class Job {
        private String avatar;
        private String title;
        private String companyName;
        private String salary;
        private String address;

        public Job(String avatar, String title, String companyName, String salary, String address) {
            this.avatar = avatar;
            this.title = title;
            this.companyName = companyName;
            this.salary = salary;
            this.address = address;
        }

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getSalary() {
			return salary;
		}

		public void setSalary(String salary) {
			this.salary = salary;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
        
        

        // Các getter và setter
    }

}

package me.shrestho.minimalcms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.repository.PageRepository;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    public void addPage() {

    }
}

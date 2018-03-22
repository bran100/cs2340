//
//  SearchViewController.swift
//  Homeless Helper
//
//  Created by Armand Raynor on 3/9/18.
//  Copyright © 2018 Armand Raynor. All rights reserved.
//

import UIKit
import YNSearch

class SearchViewController: YNSearchViewController {


    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.

        let demoDatabase = ["Menu", "Animation", "Transition", "TableView", "CollectionView", "Indicator", "Alert", "UIView", "UITextfield", "UITableView", "Swift", "iOS", "Android"]

        ynSerach.setCategories(value: demoDatabase)
        ynSerach.setSearchHistories(value: demoDatabase)

        self.ynSearchinit()

        self.initData(database: demoDatabase)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

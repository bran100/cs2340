//
//  HomeViewController.swift
//  Homeless Helper
//
//  Created by Armand Raynor on 2/19/18.
//  Copyright © 2018 Armand Raynor. All rights reserved.
//

import UIKit
import Foundation
import CSV

class HomeViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var buttonControl: UISegmentedControl!

    var selectedIndex = 0
    var searchActive: Bool = false

    var shelters = [String]()
    var restrictions = [String]()
    var filteredShelters = [String]()

    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.delegate = self
        tableView.dataSource = self

        GlobalVariables.shelterDict.removeAll()
        loadShelterData()
        GlobalVariables.shelterDict.remove(at: 0)

        // Configure Search Bar
        searchBar.delegate = self
        searchActive = false

        populateShelters()
        populateRestrictions()
    }

    func populateShelters() {
        for shelter in GlobalVariables.shelterDict {
            shelters.append(shelter[1])
        }
    }

    func populateRestrictions() {
        for shelter in GlobalVariables.shelterDict {
            restrictions.append(shelter[3])
        }
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if searchActive {
            return filteredShelters.count
        }
        return GlobalVariables.shelterDict.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        let cell : UITableViewCell = tableView.dequeueReusableCell(withIdentifier: "cell")!

        if searchActive {
            cell.textLabel?.text = filteredShelters[indexPath.row]
        } else {
            cell.textLabel?.text = shelters[indexPath.row]
        }

        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if (searchActive) {
            selectedIndex = shelters.index(of: filteredShelters[indexPath.row])!
        } else {
            selectedIndex = indexPath.row
        }
        performSegue(withIdentifier: "homeToShelter", sender: self)
    }

    func loadShelterData() {
        let stream = InputStream(fileAtPath: "/Users/Armand/Desktop/Georgia Tech/Spring 2018/CS 2340/Homeless Helper/Homeless Shelter Database.csv")!
        let csv = try! CSVReader(stream: stream)
        while let row = csv.next() {
            GlobalVariables.shelterDict.append(row)
        }
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "homeToShelter") {
            let viewController = segue.destination as! ShelterViewController
            viewController.index = selectedIndex
        }
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func buttonValueChanged(_ sender: Any) {

        var filter = ""

        switch buttonControl.selectedSegmentIndex {
            case 0:
                filter = "Men"
                break
            case 1:
                filter = "Women"
                break
            case 2:
                filter = "Newborns"
                break
            case 3:
                filter = "Children"
                break
            case 4:
                filter = "Young Adult"
                break
            default:
                break
        }

        var count = 0
        var searchText = ""
        filteredShelters = shelters.filter({ (text) -> Bool in
            searchText = restrictions[count]
            count += 1
            if (searchText.lowercased().contains(filter.lowercased())) {
                if (filter == "Men") {
                    return !searchText.lowercased().contains("wo")
                }
                return true
            }
            return false
        })



        print(filteredShelters)

        if filteredShelters.count == 0 {
            searchActive = false
        } else {
            searchActive = true
        }
        self.tableView.reloadData()
    }

}

extension HomeViewController: UISearchBarDelegate {

    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        searchActive = true
    }

    func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        searchActive = false
    }

    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchActive = false
    }

    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchActive = false
    }

    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {

        filteredShelters = shelters.filter({ (text) -> Bool in
            return text.lowercased().contains(searchText.lowercased())
        })

        if filteredShelters.count == 0 {
            searchActive = false
        } else {
            searchActive = true
        }
        self.tableView.reloadData()
    }

}

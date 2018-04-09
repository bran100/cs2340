//
//  ShelterViewController.swift
//  
//
//  Created by Armand Raynor on 2/26/18.
//

import UIKit

class ShelterViewController: UIViewController {

    @IBOutlet weak var addressData: UILabel!
    @IBOutlet weak var restrictionData: UILabel!
    @IBOutlet weak var notesData: UILabel!
    @IBOutlet weak var capacityData: UILabel!
    @IBOutlet weak var longlatData: UILabel!
    @IBOutlet weak var shelterName: UILabel!
    @IBOutlet weak var phoneNumber: UILabel!

    var index = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        shelterName.text = GlobalVariables.shelterDict[index][1]
        capacityData.text = GlobalVariables.shelterDict[index][2]
        restrictionData.text = GlobalVariables.shelterDict[index][3]
        longlatData.text = "( " + GlobalVariables.shelterDict[index][4] + "," +
            GlobalVariables.shelterDict[index][5] + " )"
        addressData.text = GlobalVariables.shelterDict[index][6]
        notesData.text = GlobalVariables.shelterDict[index][7]
        phoneNumber.text = GlobalVariables.shelterDict[index][8]
        // Do any additional setup after loading the view.
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

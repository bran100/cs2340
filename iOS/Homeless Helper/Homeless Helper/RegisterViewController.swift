//
//  RegisterViewController.swift
//  Homeless Helper
//
//  Created by Armand Raynor on 2/19/18.
//  Copyright © 2018 Armand Raynor. All rights reserved.
//

import UIKit
import Material

class RegisterViewController: UIViewController {


    @IBOutlet weak var firstnameField: TextField!
    @IBOutlet weak var lastnameField: TextField!
    @IBOutlet weak var emailField: TextField!
    @IBOutlet weak var passwordField: TextField!
    @IBOutlet weak var confirmPasswordField: TextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func checkFields() -> Bool {
        if (firstnameField.isEmpty || lastnameField.isEmpty || emailField.isEmpty || passwordField.isEmpty || confirmPasswordField.isEmpty) {
            return false
        }
        if (!((emailField.text?.contains("@"))!)) {
            let alert = UIAlertController(title: "Invalid Email",
                                          message: "Please enter a valid email address",
                                          preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Okay", style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            emailField.text = ""
            return false
        }

        if (passwordField.text != confirmPasswordField.text) {
            let alert = UIAlertController(title: "Invalid Password",
                                          message: "Passwords do not match, please try again",
                                          preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Okay", style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            passwordField.text = ""
            confirmPasswordField.text = ""
            return false
        }
        return true
    }

    @IBAction func registerButtonClicked(_ sender: Any) {
        if (checkFields()) {
            GlobalVariables.users[emailField.text!] = passwordField.text
            performSegue(withIdentifier: "registerToHome", sender: self)
        }
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

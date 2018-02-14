//
//  ViewController.swift
//  Homeless Helper
//
//  Created by Armand Raynor on 2/14/18.
//  Copyright © 2018 Armand Raynor. All rights reserved.
//

import UIKit
import Material

class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var usernameField: TextField!
    @IBOutlet weak var passwordField: TextField!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func loginClicked(_ sender: Any) {
        print(usernameField.text)
        if (usernameField.text == "user" && passwordField.text == "pass") {
            performSegue(withIdentifier: "toHome", sender: self)
        } else {
            let alert = UIAlertController(title: "Whoops!",
                                          message: "We couldn't seem to find your account. Please try again.",
                                          preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Okay", style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            self.usernameField.text = ""
            self.passwordField.text = ""
        }
    }
}

